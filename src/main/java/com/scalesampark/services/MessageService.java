package com.scalesampark.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scalesampark.dao.MessageDao;
import com.scalesampark.domains.Message;
import com.scalesampark.domains.Participant;
import com.scalesampark.util.EncriptionDecryption;

/**
 * MessageService class is working as a mediator between Controller and
 * MessageDao..
 *
 */
@Service
public class MessageService {
	@Autowired
	MessageDao messageDao;

	@Autowired
	ParticipantService participantService;

	final String ENCRYPT_DECRYPT_KEY = "Dqr12xyz12key123";
	final String ALGORITHM_NAME = "AES";

	/**
	 * This method is used to get the encrypted messages from the database and
	 * de-crypt them all and send them to the calling class or method.
	 * 
	 * @return List<Message>
	 */
	public List<Message> getAllMessages() {
		String sql = "SELECT * FROM message";
		List<Message> messages = messageDao.getAllMessages(sql);
		List<Message> decryptdMessages = getDecryptedMessages(messages);
		return decryptdMessages;
	}

	/**
	 * This method is used to get the encrypted messages from the database on the
	 * basis of participant id and de-crypt them all and send them to the calling
	 * class or method.
	 * 
	 * @return List<Message>
	 */
	public List<Message> getAllUnseenMessagesByParticipant(long participantId) throws Exception {
		Participant participant = participantService.getParticipantById(participantId);
		Object[] objs = new Object[] { participant.getLastSeenMsgId(), participant.getParticipantUuid() };
		String sql = "SELECT * FROM message WHERE message_uuid > ? AND participant_uuid != ?";

		List<Message> encryptedMessages = messageDao.getAllMessages(sql, objs);

		Collections.sort(encryptedMessages);
		Optional<Message> maxMessage = encryptedMessages.stream()
				.max((o1, o2) -> o1.getMessageUuid().compareTo(o2.getMessageUuid()));
		if (maxMessage.isPresent()) {
			Message message1 = maxMessage.get();
			participant.setLastSeenMsgId(message1.getMessageUuid());
		}
		// System.out.println(participantService.update(participant));
		List<Message> decryptdMessages = getDecryptedMessages(encryptedMessages);

		return decryptdMessages;
	}

	/**
	 * Thid method takes the list of encrypted messages and send them back
	 * decrypted.
	 * 
	 * @param encryptedMessages
	 *            List<Message>
	 * @return List<Message> decryptedMessages
	 */
	private List<Message> getDecryptedMessages(List<Message> encryptedMessages) {
		List<Message> decryptedMessages = new ArrayList<>();
		encryptedMessages.stream().forEach(msg -> {
			Message message = msg;
			String decryptedMessage = null;
			SecretKey key = new SecretKeySpec(ENCRYPT_DECRYPT_KEY.getBytes(), ALGORITHM_NAME);
			try {
				EncriptionDecryption encriptionDecryption = new EncriptionDecryption(key);
				decryptedMessage = encriptionDecryption.decrypt(msg.getMessage());
				message.setMessage(decryptedMessage);
				decryptedMessages.add(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		return decryptedMessages;
	}

	/**
	 * This method is used to save the Message object to database.
	 * 
	 * @param message
	 *            Message
	 * @return long new messageId
	 * @throws Exception
	 */
	public long saveMessage(Message message) throws Exception {
		String encryptedMessage = null;
		SecretKey key = new SecretKeySpec(ENCRYPT_DECRYPT_KEY.getBytes(), ALGORITHM_NAME);
		EncriptionDecryption encriptionDecryption = new EncriptionDecryption(key);
		encryptedMessage = encriptionDecryption.encrypt(message.getMessage());
		message.setMessage(encryptedMessage);

		return messageDao.saveMessage(message);
	}

	/**
	 * This method is used to delete the record from the database.
	 * 
	 * @param id
	 *            Long
	 * @return int count
	 */
	public int deleteMessages(Long id) {
		return messageDao.deleteMessage(id);
	}
}

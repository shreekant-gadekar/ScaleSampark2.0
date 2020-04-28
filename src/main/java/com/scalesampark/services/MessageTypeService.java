package com.scalesampark.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.scalesampark.dao.MessageTypeDao;
import com.scalesampark.domains.MessageType;

/**
 * MessageTypeService is working as a mediater between messageType controller
 * and messageType dao.
 *
 */
@Service
public class MessageTypeService {
	@Autowired
	MessageTypeDao messageTypeDao;

	/**
	 * This method is used to save the messageType object to database.
	 * 
	 * @param messageType
	 *            MessageType
	 * @return long messageTypeId
	 * @throws SQLException
	 */
	public long saveMessageType(MessageType messageType) throws SQLException {
		return messageTypeDao.saveMessageType(messageType);
	}

	/**
	 * This method is used to get the list of MessageType objects from database.
	 * 
	 * @return List<MessageType>
	 * @throws DataAccessException
	 */
	public List<MessageType> getAllMessageTypes() throws DataAccessException {
		return messageTypeDao.getAll();
	}

	/**
	 * This method is used to get the MessageType object from the database using
	 * messageTypeId.
	 * 
	 * @param messageTypeId
	 *            Long
	 * @return MessageType
	 * @throws DataAccessException
	 */
	public MessageType getMessageTypeById(Long messageTypeId) throws DataAccessException {
		return messageTypeDao.getMessageTypeById(messageTypeId);
	}

	/**
	 * This method is used to delete the MessageType object from the database using
	 * messageTypeId.
	 * 
	 * @param messageTypeId
	 *            Long
	 * @return MessageType
	 * @throws DataAccessException
	 */
	public int deleteMessageTypeById(Long id) throws SQLException {
		return messageTypeDao.deleteMessageType(id);
	}
}
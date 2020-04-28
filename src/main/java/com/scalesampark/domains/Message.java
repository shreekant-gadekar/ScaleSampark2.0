package com.scalesampark.domains;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;

/**
 * Message domain class is to create the object or hold the object from the database.
 */
public class Message implements Comparable<Message> {
	
	private Long messageUuid;
	private long participantUuid;
	private Long messageTypeId;
	@NotEmpty(message = "{error.message.notBlank}")
	private String message;
	private Timestamp createdOn;
	
	
	public Message() {}
	
	public Message(long participantUuid, Long messageTypeId, String message) {
		super();
		this.participantUuid = participantUuid;
		this.messageTypeId = messageTypeId;
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the messageTypeId
	 */
	public Long getMessageTypeId() {
		return messageTypeId;
	}

	/**
	 * @param messageTypeId the messageTypeId to set
	 */
	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}

	/**
	 * @return the participantUuid
	 */
	public long getParticipantUuid() {
		return participantUuid;
	}

	/**
	 * @param participantUuid the participantUuid to set
	 */
	public void setParticipantUuid(long participantUuid) {
		this.participantUuid = participantUuid;
	}

	@Override
	public int compareTo(Message o) {
		return this.messageUuid.compareTo(o.getMessageUuid());
	}

	/**
	 * @return the messageUuid
	 */
	public Long getMessageUuid() {
		return messageUuid;
	}

	/**
	 * @param messageUuid the messageUuid to set
	 */
	public void setMessageUuid(Long messageUuid) {
		this.messageUuid = messageUuid;
	}

	/**
	 * @return the createdOn
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Message domain class string representation method.
	 */
	@Override
	public String toString() {
		return "Message [messageUuid=" + messageUuid + ", participantUuid=" + participantUuid + ", messageTypeId="
				+ messageTypeId + ", message=" + message + ", createdOn=" + createdOn + "]";
	}
}

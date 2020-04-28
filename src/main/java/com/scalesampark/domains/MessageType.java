package com.scalesampark.domains;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.scalesampark.validator.Regex;

/**
 * MessageType domain class is to create the object or hold the object from the database.
 */
public class MessageType {
	
	private long id;
	
	@NotEmpty(message = "{error.messageType.name.not.blank}")
	@NotNull(message = "{error.messageType.name.not.blank}")
	@Regex(value = "^[a-zA-Z]*$", message = "{error.messageType.name.alphabetsOnly}")
	private String messageTypeName;
	
	public MessageType() {}
	
	public MessageType(long id, String messageTypeName) {
		this.id = id;
		this.messageTypeName = messageTypeName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMessageTypeName() {
		return messageTypeName;
	}
	public void setMessageTypeName(String messageTypeName) {
		this.messageTypeName = messageTypeName;
	}
	
	/**
	 * MessageType domain class string representation method.
	 */
	@Override
	public String toString() {
		return "MessageType [id=" + id + ", messageTypeName=" + messageTypeName + "]";
	}
}

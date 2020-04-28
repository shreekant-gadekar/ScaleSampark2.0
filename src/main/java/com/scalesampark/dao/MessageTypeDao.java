package com.scalesampark.dao;

import java.util.List;

import com.scalesampark.domains.MessageType;

public interface MessageTypeDao {
	
	/**
	 * This method is used to save the MessageType in the database.
	 * 
	 * @param messageType MessageType
	 * @return Long id
	 */
	public Long saveMessageType(MessageType messageType);
	
	/**
	 * This method is used to delete the MessageType from the database.
	 * 
	 * @param id Long
	 * @return int
	 */
	public int deleteMessageType(Long id);
	
	/**
	 * This method is used to get the MessageType object from the database
	 * using messageTypeId.
	 * 
	 * @param id Long
	 * @return MessageType
	 */
	public MessageType getMessageTypeById(Long id);
	
	/**
	 * This method is used to get the list of MessageType objects from the database.
	 * 
	 * @return List<MessageType>
	 */
	public List<MessageType> getAll();
}

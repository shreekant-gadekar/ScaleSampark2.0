package com.scalesampark.dao;

import java.sql.SQLException;
import java.util.List;

import com.scalesampark.domains.Message;

public interface MessageDao {
	
	/**
	 * This method is used to insert the Message object into database.
	 * 
	 * @param message Message
	 * @return long id
	 * @throws SQLException
	 */
	public Long saveMessage(Message message) throws Exception;
	
	/**
	 * This method is used to update the Message object into database.
	 * 
	 * @param message Message
	 * @return long id
	 */
	public Long updateMessage(Message message);
	
	/**
	 * This method is used to delete the Message object from database.
	 * 
	 * @param id Long
	 * @return count int
	 */
	public int deleteMessage(Long id);
	
	/**
	 * This method is used to get the Message object from the database.
	 * 
	 * @param id Long
	 * @return Message
	 */
	public Message getMessageById(Long id);
	
	/**
	 * This method is used to get the list of Message objects from the database.
	 * 
	 * @param sql String
	 * @param args Object (vararg)
	 * @return List<Message>
	 */
	public List<Message> getAllMessages(String sql, Object... args);
}

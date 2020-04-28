package com.scalesampark.dao;

import java.sql.SQLException;
import java.util.List;

import com.scalesampark.domains.Participant;

public interface ParticipantDao {
	
	/**
	 * This method is used to insert the Participant object into the database.
	 * 
	 * @param participant Participant
	 * @return id Long
	 * @throws SQLException
	 */
	public long saveParticipant(Participant participant) throws SQLException;
	
	/**
	 * This method is used to update the Participant object into the database.
	 * 
	 * @param participant Participant
	 * @return
	 */
	public int updateParticipant(Participant participant);
	
	/**
	 * This method is used to delete the Participant object from database using
	 * participantId.
	 *  
	 * @param id Long
	 * @return count int
	 */
	public int deleteParticipant(Long id);
	
	/**
	 * This method is used to get the Participant object from the database using
	 * participantId.
	 * 
	 * @param id Long
	 * @return Participant
	 */
	public Participant getParticipantById(Long id);
	
	/**
	 * This method is used to get the list of records from the database.
	 * 
	 * @return List<Participant>
	 */
	public List<Participant> getAllParticipants();
	
	/**
	 * @param email
	 * @return
	 */
	public Participant getParticipantByEmail(String email);
}

package com.scalesampark.services;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.scalesampark.dao.ParticipantDao;
import com.scalesampark.domains.Employee;
import com.scalesampark.domains.Participant;

/**
 * ParticipantService is working as mediater between ParticipantController and
 * ParticipantDao.
 *
 */
@Service
public class ParticipantService {
	Logger logger = LoggerFactory.getLogger(Participant.class);
	@Autowired
	ParticipantDao participantDao;

	@Autowired
	MessageService messageService;

	@Autowired
	EmployeeService employeeService;

	public ParticipantService() {
	}

	/**
	 * This methos is used to save the participant to the database.
	 * 
	 * @param participant
	 *            Participant
	 * @return long participantId
	 * @throws SQLException
	 */
	private long addParticipant(Participant participant) throws SQLException {
		return participantDao.saveParticipant(participant);
	}

	/**
	 * This methos is used to get the Participant object from the database using
	 * Participant id.
	 * 
	 * @param participantId
	 *            long
	 * @return Participant
	 * @throws DataAccessException
	 * @throws Exception
	 */
	public Participant getParticipantById(long participantId) throws DataAccessException, Exception {
		return participantDao.getParticipantById(participantId);
	}

	/**
	 * This method is used to get the list of Participant objects from the database.
	 * 
	 * @return List<Participant>
	 */
	public List<Participant> getAllParticipants() {
		List<Participant> participants = participantDao.getAllParticipants();
		return participants;
	}

	/**
	 * This method is used to save the participant to the database.
	 * 
	 * @param participant
	 *            Participant
	 * @return long participantId
	 * @throws SQLException
	 */
	public long saveParticipant(Participant participant) throws SQLException {
		return addParticipant(participant);
	}

	/**
	 * This method is used to update the participant to the database.
	 * 
	 * @param participant
	 *            Participant
	 * @return long participantId
	 * @throws SQLException
	 */
	public int update(Participant t) throws SQLException {
		return participantDao.updateParticipant(t);
	}

	/**
	 * This method is used to delete the participant record from the database as per
	 * given participantId.
	 * 
	 * @param id
	 *            long
	 * @return int count
	 * @throws SQLException
	 */
	public int delete(long id) throws SQLException {
		return participantDao.deleteParticipant(id);
	}

	/**
	 * This method is used to get the Participant record from database using
	 * participant email id.
	 * 
	 * @param email
	 * @return
	 * @throws DataAccessException
	 */
	public Participant getParticipantByEmail(String email) throws DataAccessException {
		Participant participantByEmail = null;
		try {
			return participantDao.getParticipantByEmail(email);
		} catch (DataAccessException e) {
			logger.info("in catch block");
			e.printStackTrace();
			return participantByEmail;
		}
	}

	/**
	 * This method is used to validate the participant is valid or not using
	 * employeeService and providing participant email id.
	 * 
	 * @param participant
	 *            Participant
	 * @return boolean
	 * @throws Exception
	 */
	public boolean isValidParticipant(Participant participant) throws Exception {
		Employee employees = employeeService.getEmployeeByEmail(participant.getEmail());
		return (employees != null);
	}

	/**
	 * This method is used to check is participant already present or not.
	 * 
	 * @param participant
	 *            Participant
	 * @return boolean
	 */
	public boolean isParticipantAlreadyPresent(Participant participant) {
		Participant participantByEmail = getParticipantByEmail(participant.getEmail());
		return (participantByEmail != null);
	}
}
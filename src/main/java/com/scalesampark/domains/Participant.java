package com.scalesampark.domains;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.scalesampark.validator.Regex;

/**
 * Participant domain class is to create the object or hold the object from the database.
 */
public class Participant {

	private Long participantUuid;
	@NotEmpty(message = "{error.participant.email.not.empty}")
	@Email(message = "{error.participant.email.valid}")
	private String email;
	
	@Regex(value = "^[a-zA-Z]*$", message = "{error.participant.nickname.alphabetsOnly}")
	private String nickname;
	
	private Timestamp lastSeen;
	private Long lastSeenMsgId = 0L;

	public Participant(){}
	
	/**
	 * @param participantUuid
	 * @param email
	 * @param nickname
	 * @param lastSeen
	 * @param lastSeenMsgId
	 */
	public Participant(Long participantUuid, String email, String nickname, Timestamp lastSeen, Long lastSeenMsgId) {
		super();
		this.participantUuid = participantUuid;
		this.email = email;
		this.nickname = nickname;
		this.lastSeen = lastSeen;
		this.lastSeenMsgId = lastSeenMsgId;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * @return the lastSeenMsgId
	 */
	public Long getLastSeenMsgId() {
		return lastSeenMsgId;
	}

	/**
	 * @param lastSeenMsgId the lastSeenMsgId to set
	 */
	public void setLastSeenMsgId(Long lastSeenMsgId) {
		this.lastSeenMsgId = lastSeenMsgId;
	}

	/**
	 * @return the participantUuid
	 */
	public Long getParticipantUuid() {
		return participantUuid;
	}

	/**
	 * @param participantUuid the participantUuid to set
	 */
	public void setParticipantUuid(Long participantUuid) {
		this.participantUuid = participantUuid;
	}

	/**
	 * @return the lastSeen
	 */
	public Timestamp getLastSeen() {
		return lastSeen;
	}

	/**
	 * @param lastSeen the lastSeen to set
	 */
	public void setLastSeen(Timestamp lastSeen) {
		this.lastSeen = lastSeen;
	}

	/**
	 * Participant domain class string representation method. 
	 */
	@Override
	public String toString() {
		return "Participant [participantUuid=" + participantUuid + ", email=" + email + ", nickname=" + nickname
				+ ", lastSeen=" + lastSeen + ", lastSeenMsgId=" + lastSeenMsgId + "]";
	}
}

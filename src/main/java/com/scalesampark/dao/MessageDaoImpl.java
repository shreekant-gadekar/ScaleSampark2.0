package com.scalesampark.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scalesampark.domains.Message;
import com.scalesampark.domains.Participant;
import com.scalesampark.services.ParticipantService;
import com.scalesampark.util.DateUtil;

@Repository
public class MessageDaoImpl extends JdbcDaoSupport implements MessageDao {
	@Autowired
	DataSource dataSource;

	@Autowired
	ParticipantService participantService;
	
	@Autowired
	DateUtil dateUtil;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	@Override
	public Long saveMessage(Message msg) throws Exception {
		String sql = "INSERT INTO message " +
				"(message_type_id, message, created_on, participant_uuid) VALUES (?, ?, ?, ?)" ;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, msg.getMessageTypeId());
			ps.setString(2, msg.getMessage());
			ps.setTimestamp(3, dateUtil.getCurrentDateTime());
			ps.setLong(4, msg.getParticipantUuid());
			
			return ps;
		}, keyHolder);
		
		Participant participant = participantService.getParticipantById(msg.getParticipantUuid());
		participantService.saveParticipant(participant);
		
		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Long updateMessage(Message e) {
		return null;
	}

	@Override
	public int deleteMessage(Long id) {
		String sql = "DELETE FROM message where participant_uuid = ?";
		return getJdbcTemplate().update(sql, id);
	}

	@Override
	public Message getMessageById(Long id) {
		return null;
	}

	@Override
	public List<Message> getAllMessages(String sql, Object... args) {
		List<Message> result = getJdbcTemplate().query(
				sql, 
				BeanPropertyRowMapper.newInstance(Message.class), 
				args);
		return result;
	}
}

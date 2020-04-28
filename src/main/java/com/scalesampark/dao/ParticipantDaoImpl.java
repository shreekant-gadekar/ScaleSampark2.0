package com.scalesampark.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.scalesampark.domains.Participant;
import com.scalesampark.services.MessageService;
import com.scalesampark.util.DateUtil;

@Repository
public class ParticipantDaoImpl extends JdbcDaoSupport implements ParticipantDao {

	@Autowired
	DateUtil dateUtil;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	MessageService messageService;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	public ParticipantDaoImpl() {
	}

	@Override
	public long saveParticipant(Participant e) throws SQLException {
		String sql = "INSERT INTO participant " + 
				"(email, nickname) VALUES (?, ?)" ;
		KeyHolder keyHolder = new GeneratedKeyHolder();
		dateUtil.getCurrentDateTime();
		getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, e.getEmail());
			ps.setString(2, e.getNickname());
			return ps;
		}, keyHolder);
		
		return keyHolder.getKey().longValue();
	}

	@Override
	public int updateParticipant(Participant e) {
		String sql = "UPDATE participant SET last_seen_msg_id = ? WHERE participant_uuid = ?";
		return getJdbcTemplate().update(sql, e.getLastSeenMsgId(), e.getParticipantUuid());
	}

	@Override
	public int deleteParticipant(Long id) {
		System.out.println("messages deleted : "+messageService.deleteMessages(id));
		String sql = "DELETE from participant WHERE participant_uuid = ?";
		return getJdbcTemplate().update(sql, id);
	}

	@Override
	public Participant getParticipantById(Long id) {
		String sql = "SELECT * FROM participant " +
					"WHERE participant_uuid = ?" ;
		return getJdbcTemplate().queryForObject(
				sql, 
				new Object[] {id}, 
				BeanPropertyRowMapper.newInstance(Participant.class)
				);
	}

	@Override
	public List<Participant> getAllParticipants() {
		String sql = "SELECT * FROM participant";
		List<Participant> result = getJdbcTemplate()
				.query(sql, 
					BeanPropertyRowMapper.newInstance(Participant.class)); 
		return result;
	}

	@Override
	public Participant getParticipantByEmail(String email) throws DataAccessException {
		String sql = "SELECT * FROM participant " +
				"WHERE email = ?" ;
	return getJdbcTemplate().queryForObject(
			sql, 
			new Object[] {email}, 
			BeanPropertyRowMapper.newInstance(Participant.class)
			);
	}
}

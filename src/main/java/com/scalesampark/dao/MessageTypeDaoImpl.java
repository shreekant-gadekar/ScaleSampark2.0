package com.scalesampark.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.scalesampark.domains.MessageType;

@Service
public class MessageTypeDaoImpl extends JdbcDaoSupport implements MessageTypeDao {
	@Autowired
	DataSource dataSource;

	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	@Override
	public Long saveMessageType(MessageType msg) {
		String sql = "INSERT INTO message_type " +
				"(message_type_name) VALUES (?)" ;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, msg.getMessageTypeName());
			return ps;
		}, keyHolder);
		return keyHolder.getKey().longValue();
	}

	@Override
	public int deleteMessageType(Long id) {
		String sql = "DELETE FROM message_type where id = ?";
		return getJdbcTemplate().update(sql, id);
	}

	@Override
	public MessageType getMessageTypeById(Long id) throws EmptyResultDataAccessException {
		String sql = "SELECT * FROM message_type WHERE id = ?";
		MessageType result = getJdbcTemplate().queryForObject(
				sql,
				new Object[] {id},
				BeanPropertyRowMapper.newInstance(MessageType.class));
		return result;
	}

	@Override
	public List<MessageType> getAll() {
		String sql = "SELECT * FROM message_type";
		List<MessageType> result = getJdbcTemplate().query(
				sql, 
				BeanPropertyRowMapper.newInstance(MessageType.class));
		return result;
	}
}

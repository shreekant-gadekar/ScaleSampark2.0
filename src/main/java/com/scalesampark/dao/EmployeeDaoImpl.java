/**
 * 
 */
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

import com.scalesampark.domains.Employee;

@Repository
public class EmployeeDaoImpl extends JdbcDaoSupport implements EmployeeDao {

	@Autowired
	DataSource dataSource;
	
	public EmployeeDaoImpl() {} 
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	
	@Override
	public long saveEmployee(Employee emp) throws SQLException {
		String sql = "INSERT INTO employee " +
				"(name, email) VALUES (?, ?)" ;
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		getJdbcTemplate().update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getEmail());
			return ps;
		}, keyHolder);

		return (long) keyHolder.getKey().longValue();
	}

	@Override
	public Long updateEmployee(Employee e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEmployee(Long id) {

	}

	@Override
	public Employee getEmployeeById(Long id) throws Exception {
		String sql = "SELECT * FROM employee WHERE id = ?";
		return getJdbcTemplate()
				.queryForObject(sql,
					new Object[] {id},
					BeanPropertyRowMapper.newInstance(Employee.class));
	}

	@Override
	public List<Employee> getAllEmployees() {
		String sql = "SELECT * FROM employee";
		List<Employee> result = getJdbcTemplate().query(sql,BeanPropertyRowMapper.newInstance(Employee.class));
		return result;
	}


	@Override
	public Employee getEmployeeByEmail(String email)  throws DataAccessException {
		String sql = "SELECT * FROM employee WHERE email = ?";
		return getJdbcTemplate()
				.queryForObject(sql,
					new Object[] {email},
					BeanPropertyRowMapper.newInstance(Employee.class));
	}
}

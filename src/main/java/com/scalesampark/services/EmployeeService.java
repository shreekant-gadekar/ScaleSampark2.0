package com.scalesampark.services;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.scalesampark.dao.EmployeeDao;
import com.scalesampark.domains.Employee;

/**
 * EmployeeService class is working as a mediater between EmployeeController and
 * EmployeeDao.
 * 
 * It fetches the records from database using EmployeeDao and pass it 
 * to EmployeeController. 
 *
 */
@Service
public class EmployeeService {

	Logger logger = LoggerFactory.getLogger(EmployeeService.class);

	@Autowired
	EmployeeDao employeeDao;

	public EmployeeService() {}

	/**
	 * This method get the employee bean from the controller and pass 
	 * it to EmployeeDao insert it into database.
	 * 
	 * @param employee Eemployee
	 * @return new employee id long
	 * @throws SQLException
	 */
	private long addEmployee(Employee employee) throws SQLException {
		return employeeDao.saveEmployee(employee);
	}

	/**
	 * This method is used to get the employee object from the database using
	 * employeeID as a parameter.
	 * 
	 * @param employeeId long
	 * @return Employee
	 * @throws Exception
	 */
	public Employee getEmployeeById(long employeeId) throws Exception {
		try {
			return employeeDao.getEmployeeById(employeeId);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * This method is used to get the list of Employee objects from the 
	 * database using EmployeeDao class.
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> getAllEmployees() {
		return employeeDao.getAllEmployees();
	}

	/**
	 * This method is used to save the record into database.
	 * 
	 * @param employee Employee
	 * @return employeeId
	 * @throws SQLException
	 */
	public long saveEmployee(Employee employee) throws SQLException {
		long id = addEmployee(employee);
		return id;
	}

	public void update(Employee emp) {
	}

	public int delete(long t) {
		return 0;
	}

	/**
	 * This method is used to get Employe object from the database using 
	 * email id for searching it.
	 * 
	 * This method is mostly used by participant service class to verify 
	 * the entered participant detail is valid or not.
	 * 
	 * @param email String
	 * @return Employee
	 * @throws DataAccessException
	 */
	public Employee getEmployeeByEmail(String email) throws DataAccessException {
		try {
			return employeeDao.getEmployeeByEmail(email);
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.info("in catch block");
			return null;
		}
	}
}
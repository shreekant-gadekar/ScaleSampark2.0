package com.scalesampark.dao;

import java.sql.SQLException;
import java.util.List;

import com.scalesampark.domains.Employee;

public interface EmployeeDao {
	/**
	 * This method is used to insert the employee object into database.
	 * 
	 * @param employee Employee
	 * @return long id
	 * @throws SQLException
	 */
	public long saveEmployee(Employee employee) throws SQLException ;
	
	/**
	 * This method is used to update the employee object into database.
	 * 
	 * @param employee Employee
	 * @return long id
	 */
	public Long updateEmployee(Employee e);
	
	/**
	 * This method is used to delete the employee object from database.
	 * 
	 * @param id Long
	 */
	public void deleteEmployee(Long id);
	
	/**
	 * This method is used to get an employee object from database
	 * using employee id.
	 * 
	 * @param id Long
	 * @return Employee
	 */
	public Employee getEmployeeById(Long id) throws Exception ;
	
	/**
	 * This method is used to get the list of employee objects from database.
	 * 
	 * @return List<Employee>
	 */
	public List<Employee> getAllEmployees();
	
	/**
	 * This method is used to get the employee object from the database
	 * using employee email id.
	 * 
	 * @param email String
	 * @return Employee
	 */
	public Employee getEmployeeByEmail(String email);
}
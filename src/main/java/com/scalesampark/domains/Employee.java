package com.scalesampark.domains;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.scalesampark.validator.Regex;

/**
 * Domain class is to create the object or hold the object from the database.
 *
 */
public class Employee {
	private long id;
	
	@NotEmpty(message = "{error.employee.email.not.empty}")
	@Email(message = "{error.employee.email.valid}")
	private String email;
	
	@NotEmpty(message = "{error.employee.name.not.empty}")
	@Regex(value = "^[a-zA-Z]*$", message = "{error.employee.name.alphabetsOnly}")
	private String name;

	public Employee() {}
	
	public Employee(long id, String email, String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
	}

	/**
	 * @return the employeeId
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setId(long employeeId) {
		this.id = employeeId;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Employee class string representation.
	 */
	@Override
	public String toString() {
		return "Employee [employeeId=" + id + ", email=" + email + ", name=" + name + "]";
	}
}

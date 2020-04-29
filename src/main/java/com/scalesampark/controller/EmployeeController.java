package com.scalesampark.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scalesampark.domains.Employee;
import com.scalesampark.services.EmployeeService;
import com.scalesampark.util.HttpStatusMapsConstants;
import com.scalesampark.validator.ValidatorUtil;

@RestController
@RequestMapping("/employees")
@Validated
public class EmployeeController {

	Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	EmployeeService employeeService;

	@Autowired
	ValidatorUtil validatorUtil;
	
	@GetMapping(produces="application/json")
	public ResponseEntity<Object> getAllEmployees() {
		List<Employee> employees = employeeService.getAllEmployees();
		Map<String, Object> map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
		map.put("data", employees);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@GetMapping(path = "/{id}", produces="application/json")
	public ResponseEntity<Object> getEmployeeById(@PathVariable("id") String stringId) throws DataAccessException, Exception {
		Employee employee = null;
		Map<String, Object> map = null;
		List<String> errors = new ArrayList<String>();

		errors = validatorUtil.validateEmployeeId(stringId, errors);

		if (errors.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			map.put("data", errors);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		Long id = Long.valueOf(stringId);

		employee = employeeService.getEmployeeById(id);
		if (employee != null) {
			map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
			map.put("data", employee);
		}
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Object> saveEmployee(@Valid @RequestBody Employee t) throws SQLException, Exception {
		Map<String, Object> map = null;
		final long id = employeeService.saveEmployee(t);
		map = HttpStatusMapsConstants.HTTP_STATUS_201_CREATED;
		map.put("message", "Employee created successfuly.");
		Map<String, Object> hashMap = new HashMap<>(1);
		hashMap.put("id", id);
		map.put("data",  hashMap);

		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}
}
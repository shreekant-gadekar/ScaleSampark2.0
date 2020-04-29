package com.scalesampark.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.scalesampark.Application;
import com.scalesampark.domains.Employee;
import com.scalesampark.services.EmployeeService;
import com.scalesampark.util.ResponseEntityNodes;
import com.scalesampark.validator.ValidatorUtil;

/**
 * This test controller is to test the EmployeeController.
 *
 */
@SpringBootTest(classes = { Application.class,
		ValidatorUtil.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {

	@InjectMocks
	EmployeeController employeeController;

	@Mock
	EmployeeService employeeService;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	ResponseEntityNodes responseEntityNodes;

	Employee employeeWithValidData1 = new Employee(26l, "shreekant.gadekar@gmail.com", "Shrikant Gadekar");
	Employee employeeWithValidData2 = new Employee(27l, "amol_jadhav@gmail.com", "Amol Jadhav");

	Employee employeeWithWrongEmail1 = new Employee(27l, "xyz@@gmail.com", "xyznick");
	Employee employeeWithWrongEmail2 = new Employee(3l, "xyzgmail.com", "xyznick");
	Employee employeeWithWrongEmail3 = new Employee(3l, "xyz@gmail..com", "xyznick");

	Employee employeeWithWrongNickname1 = new Employee(3l, "xyz@gmail.com", "xyznick123");

	@Test
	public void getAllEmployees_test() {
		List<Employee> list = new ArrayList<>();
		list.add(employeeWithValidData1);
		list.add(employeeWithValidData2);

		when(employeeService.getAllEmployees()).thenReturn(list);

		@SuppressWarnings("unchecked")
		List<Employee> resultEmployeeList = responseEntityNodes.getList.apply(employeeController.getAllEmployees());

		assertThat(resultEmployeeList.size()).isEqualTo(2);
		assertEquals(resultEmployeeList.get(0).getName(), "Shrikant Gadekar");
		assertThat(resultEmployeeList.get(1).getName()).isEqualTo("Amol Jadhav");
	}

	@Test
	public void getEmployeeById_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			// send 0 value as a path variable to get the Participant
			ResponseEntity<String> response = restTemplate.getForEntity("/employees/26", String.class);

			String expectedJSON = "{\"status\":200,\"message\":\"Successful\",\"data\":{\"id\":26,\"email\":\"shreekant.gadekar@gmail.com\",\"name\":\"Shrikant Gadekar\"},\"errors\":null}";
			assertEquals(HttpStatus.OK, response.getStatusCode());
			JSONAssert.assertEquals(expectedJSON, response.getBody(), true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void getEmployeeById_0_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// send 0 value as a path variable to get the Participant
			ResponseEntity<String> response = restTemplate.getForEntity("/employees/0", String.class);

			String expectedJSON = "{\"status\":400,\"message\":\"Bad Request\",\"data\":[\"Employe Id should not be blank or 0\"],\"errors\":null}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJSON, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}
	
	@Test
	public void getEmployeeById_with_wrong_id_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// send 310 value as a path variable to get the Employee
			ResponseEntity<String> response = restTemplate.getForEntity("/employees/310", String.class);

			String expectedJSON = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Data not found for specified details.\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJSON, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}

	@Test
	public void getEmployeeById_with_alphanumeric_value_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			// send 0 value as a path variable to get the Participant
			ResponseEntity<String> response = restTemplate.getForEntity("/employees/26aks", String.class);
			String expectedJSON = "{\"status\":400,\"message\":\"Bad Request\",\"data\":[\"Employe Id should be number only\"],\"errors\":null}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJSON, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}

	@Test
	public void saveEmployeeWithBlankEmailAndName_test() throws Exception {
		try {
			String employeeWithWrongEmail = "{\"email\":\"\",\"name\":\"\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(employeeWithWrongEmail, headers);
			// send json with POST
			ResponseEntity<String> response = restTemplate.postForEntity("/employees", entity, String.class);

			String expectedJson = "{\"status\":400,\"errors\":[\"Employee email should not be empty\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			// JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}

	@Test
	public void saveEmployeeWithBlankEmail_test() throws Exception {
		try {
			String employeeWithWrongEmail = "{\"email\":\"\",\"name\":\"Abcd\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(employeeWithWrongEmail, headers);
			// send json with POST
			ResponseEntity<String> response = restTemplate.postForEntity("/employees", entity, String.class);

			String expectedJson = "{\"status\":400,\"errors\":[\"Employee email should not be empty\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}

	@Test
	public void saveEmployeeWithWrongEmail_test() throws Exception {
		try {
			String employeeWithWrongEmail = "{\"email\":\"abcdgmail.com\",\"name\":\"Abcd\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(employeeWithWrongEmail, headers);
			// send json with POST
			ResponseEntity<String> response = restTemplate.postForEntity("/employees", entity, String.class);

			String expectedJson = "{\"status\":400,\"errors\":[\"Employee email must be valid\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}

	@Test
	public void saveEmployeeWithAlphanumericNickName_test() throws Exception {
		try {
			String employeeWithWrongEmail = "{\"email\":\"abcd@gmail.com\",\"name\":\"123ksdj\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(employeeWithWrongEmail, headers);

			// send json with POST
			ResponseEntity<String> response = restTemplate.postForEntity("/employees", entity, String.class);

			String expectedJson = "{\"status\":400,\"errors\":[\"Employee name should have alphabets Only\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}

	@Test
	public void saveEmployeeWithBlankName_test() throws Exception {
		try {
			String employeeWithWrongEmail = "{\"email\":\"abcd@gmail.com\",\"name\":\"\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(employeeWithWrongEmail, headers);

			// send json with POST
			ResponseEntity<String> response = restTemplate.postForEntity("/employees", entity, String.class);

			String expectedJson = "{\"status\":400,\"errors\":[\"Employee name should not be empty\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" + e);
		}
	}
}

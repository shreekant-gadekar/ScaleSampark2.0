package com.scalesampark.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
import com.scalesampark.domains.Participant;
import com.scalesampark.dto.ParticipantDto;
import com.scalesampark.services.ParticipantService;
import com.scalesampark.util.ResponseEntityNodes;


@SpringBootTest(classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParticipantControllerTest {

	@InjectMocks
	ParticipantController participantController;
	
	@Mock
	ParticipantService participantService;
	
    @Autowired
    private TestRestTemplate restTemplate;
	
    @Autowired
	ResponseEntityNodes responseEntityNodes;
	
	Participant participantWithValidData1 = new Participant(12l, "abcd@gmail.com", "abcd", Timestamp.valueOf(LocalDateTime.now()), 1l);
	Participant participantWithValidData2 = new Participant(2l, "xyz@gmail.com", "xyznick", Timestamp.valueOf(LocalDateTime.now()), 2l);
	
	Participant participantWithWrongEmail1 = new Participant(3l, "xyz@@gmail.com", "xyznick", Timestamp.valueOf(LocalDateTime.now()), 1l);
	Participant participantWithWrongEmail2 = new Participant(3l, "xyzgmail.com", "xyznick", Timestamp.valueOf(LocalDateTime.now()), 1l);
	Participant participantWithWrongEmail3 = new Participant(3l, "xyz@gmail..com", "xyznick", Timestamp.valueOf(LocalDateTime.now()), 1l);
	
	Participant participantWithWrongNickname1 = new Participant(3l, "xyz@gmail.com", "xyznick123", Timestamp.valueOf(LocalDateTime.now()), 1l);
	
	@Test
	public void testGetAllParticipant() {
		List<Participant> list = new ArrayList<>();
		list.add(participantWithValidData1);
		list.add(participantWithValidData2);
        
		when(participantService.getAllParticipants()).thenReturn(list);
        
		@SuppressWarnings("unchecked")
		List<ParticipantDto> resultDtoList = responseEntityNodes
				.getList.apply(participantController.getAllParticipants());
		
		assertThat(resultDtoList.size()).isEqualTo(2);
		assertEquals(resultDtoList.get(0).getNickname(),"abcd");
		assertThat(resultDtoList.get(1).getNickname()).isEqualTo("xyznick");
	}
	
	@Test
	public void getParticipantById_test() {
		/*Participant expectedParticipant = participantWithValidData1;
		try {
			when(participantService.getParticipantById(12)).thenReturn(expectedParticipant);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
		@SuppressWarnings("unchecked")
		ParticipantDto resultParticipantDto = responseEntityNodes
				.getParticipantDto.apply(participantController.get("12"));
		assertEquals(resultParticipantDto.getNickname(),expectedParticipant.getNickname());
		assertEquals(resultParticipantDto.getParticipantUuid(),expectedParticipant.getParticipantUuid().toString());
		*/
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
	        // send 0 value as a path variable to get the Participant
	        ResponseEntity<String> response = restTemplate.getForEntity("/participants/5", String.class);
	        
	        String expectedJSON = "{\"status\":200,\"message\":\"Successful\",\"data\":{\"participantUuid\":\"5\",\"nickname\":\"xyznickname\",\"lastSeen\":\"2020-04-24 15:48:03.0\"},\"errors\":null}";
	        assertEquals(HttpStatus.OK, response.getStatusCode());
			JSONAssert.assertEquals(expectedJSON, response.getBody(), false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getParticipantById_0_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send 0 value as a path variable to get the Participant
	        ResponseEntity<String> response = restTemplate.getForEntity("/participants/0", String.class);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Participant Id should not be blank or 0\"]}";
			JSONAssert.assertEquals(expectedJson , response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" +e);
		}
	}
	
	@Test
	public void getParticipantById_with_alphanumeric_Id_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send 0 value as a path variable to get the Participant
	        ResponseEntity<String> response = restTemplate.getForEntity("/participants/asd123", String.class);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Participant Id should be number only\"]}";
			JSONAssert.assertEquals(expectedJson , response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" +e);
		}
	}
	
	@Test
	public void saveDuplicateParticipant_test() {
		try {
			when(participantService.saveParticipant(participantWithValidData1)).thenReturn(12l);
			ResponseEntity<Object> responseEntity = participantController.saveParticipant(participantWithValidData1);
			int result = responseEntityNodes.getStatusCode.apply(responseEntity);
			assertEquals(204, result);
		} catch (SQLException e) {
			System.err.println(e);
		}
	}
	
	@Test
	public void saveParticipantWithWrongEmail_test() throws Exception {
		try {
			String participantWithWrongEmail = "{\"email\":\"abcdgmail.com\",\"nickname\":\"Abcd\"}";
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(participantWithWrongEmail, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/participants", entity, String.class);

	        String expectedJson = "{\"status\":400,\"errors\":[\"Participant email must be valid\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" +e);
		}
	}
	
	@Test
	public void saveParticipantWithAlphanumericNickName_test() throws Exception {
		try {
			String participantWithWrongEmail = "{\"email\":\"abcd@gmail.com\",\"nickname\":\"123ksdj\"}";
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(participantWithWrongEmail, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/participants", entity, String.class);

	        String expectedJson = "{\"status\":400,\"errors\":[\"Participant nickname should have alphabets Only\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" +e);
		}
	}
}

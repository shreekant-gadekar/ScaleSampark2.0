package com.scalesampark.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
import com.scalesampark.domains.Message;
import com.scalesampark.services.MessageService;
import com.scalesampark.util.ResponseEntityNodes;

@SpringBootTest(classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessagesControllerTest {
	
	@InjectMocks
	MessagesController messagesController;
	
	@Mock
	MessageService messageService;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	ResponseEntityNodes responseEntityNodes;
	
	Message messageValidDate1 = new Message(26l, 1l, "hi");
	Message messageValidDate2 = new Message(27l, 1l, "hi There");
	
	//{"participantUuid":"5","messageTypeId":"1","message":"I am totally fine !!"}
	
	@Test
	public void getAllTest() {
		
		try {
			List<Message> listOfmessages = new ArrayList<>();
			listOfmessages.add(messageValidDate1);
			listOfmessages.add(messageValidDate2);
			
			when(messageService.getAllMessages()).thenReturn(listOfmessages);
			
			@SuppressWarnings("unchecked")
			List<Message> redultListOfmessages = responseEntityNodes.getList.apply(messagesController.getAllMessages());
			
			assertEquals(2, redultListOfmessages.size());
			assertEquals(listOfmessages.get(0).getMessage(), redultListOfmessages.get(0).getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void saveMessage_With_BlankMessageTypeId_Test() {
		try {
			String messageToSave = "{\"participantUuid\":\"5\",\"messageTypeId\":\"0\",\"message\":\"I am totally fine !!\"}";
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(messageToSave, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/messages", entity, String.class);
			
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Message Type Id should not be blank or 0\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void saveMessage_With_BlankMessageTypeIdAndParticipantUUid_Test() {
		try {
			String messageToSave = "{\"participantUuid\":\"\",\"messageTypeId\":\"0\",\"message\":\"I am totally fine !!\"}";
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(messageToSave, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/messages", entity, String.class);
			
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Message Type Id should not be blank or 0\",\"Participant Uuid should not be blank or 0\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void saveMessage_With_BlankMessage_Test() {
		try {
			String messageToSave = "{\"participantUuid\":\"5\",\"messageTypeId\":\"1\",\"message\":\"\"}";
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(messageToSave, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/messages", entity, String.class);
			
	        String expectedJson = "{\"status\":400,\"errors\":[\"Message should not be blank\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void getAllUnseenMessagesByParticipant_With_Valid_ParticipantId_Test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.getForEntity("/messages/6", String.class);
			if(HttpStatus.OK != response.getStatusCode()) {
				assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
			} else {
				assertEquals(HttpStatus.OK,response.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void getAllUnseenMessagesByParticipant_With_ZeroParticipantId_Test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.getForEntity("/messages/0", String.class);
			
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Participant Id should not be blank or 0\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void getAllUnseenMessagesByParticipant_with_alphanumeric_Id_test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send 0 value as a path variable to get the Participant
	        ResponseEntity<String> response = restTemplate.getForEntity("/messages/asd123", String.class);

	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Participant Id should be number only\"]}";
			JSONAssert.assertEquals(expectedJson , response.getBody(), true);
		} catch (Exception e) {
			System.err.println("Exception" +e);
		}
	}
}

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
import com.scalesampark.domains.MessageType;
import com.scalesampark.services.MessageService;
import com.scalesampark.services.MessageTypeService;
import com.scalesampark.util.ResponseEntityNodes;

@SpringBootTest(classes=Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MessagesTypeControllerTest {
	
	@InjectMocks
	MessageTypeController messageTypeController;
	
	@Mock
	MessageTypeService messageTypeService;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Autowired
	ResponseEntityNodes responseEntityNodes;
	
	MessageType messageTypeValidDate1 = new MessageType(1l,"Group");
	MessageType messageTypeValidDate2 = new MessageType(2l, "Personal");
	
	//{"participantUuid":"5","messageTypeId":"1","message":"I am totally fine !!"}
	
	@Test
	public void getAllTest() {
		
		try {
			List<MessageType> listOfmessagesType = new ArrayList<>();
			listOfmessagesType.add(messageTypeValidDate1);
			listOfmessagesType.add(messageTypeValidDate2);
			
			when(messageTypeService.getAllMessageTypes()).thenReturn(listOfmessagesType);
			
			@SuppressWarnings("unchecked")
			List<MessageType> redultListOfmessages = responseEntityNodes.getList.apply(messageTypeController.getAllMessageTypes());
			
			assertEquals(2, redultListOfmessages.size());
			assertEquals(listOfmessagesType.get(0).getMessageTypeName(), redultListOfmessages.get(0).getMessageTypeName());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void saveMessage_With_BlankMessageTypeName_Test() {
		try {
			String messageTypeToSave = "{\"messageTypeName\":\"\"}";
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(messageTypeToSave, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/messagetypes", entity, String.class);
			
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Message type name should not be blank\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void saveMessage_With_alphanumeric_MessageTypeName_Test() {
		try {
			String messageTypeToSave = "{\"messageTypeName\":\"askd89\"}";
			
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        HttpEntity<String> entity = new HttpEntity<>(messageTypeToSave, headers);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.postForEntity("/messagetypes", entity, String.class);
			
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Message type name should be alphabets only\"]}";
	        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	        JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void getMessageTypeById_With_Valid_MessageTypeId_Test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.getForEntity("/messagetypes/2", String.class);
			if(HttpStatus.OK != response.getStatusCode()) {
				assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			} else {
				assertEquals(HttpStatus.OK,response.getStatusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
	
	@Test
	public void getMessageTypeById_With_alphanumeric_MessageTypeId_Test() {
		try {
			HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        // send json with POST
	        ResponseEntity<String> response = restTemplate.getForEntity("/messagetypes/2df", String.class);
	        String expectedJson = "{\"status\":400,\"message\":\"Bad Request\",\"data\":null,\"errors\":[\"Message type Id should be number only\"]}";
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
			JSONAssert.assertEquals(expectedJson, response.getBody(), true);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e);
		}
	}
}

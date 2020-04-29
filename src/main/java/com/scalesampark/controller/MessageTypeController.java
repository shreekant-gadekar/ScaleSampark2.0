package com.scalesampark.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scalesampark.domains.MessageType;
import com.scalesampark.services.MessageTypeService;
import com.scalesampark.util.HttpStatusMapsConstants;
import com.scalesampark.validator.ValidatorUtil;

@RestController
@RequestMapping("/messagetypes")
@Validated
public class MessageTypeController {
	@Autowired
	MessageTypeService messageTypeService;

	@Autowired
	ValidatorUtil validatorUtil;

	@GetMapping(produces="application/json")
	public ResponseEntity<Object> getAllMessageTypes() throws Exception {
		Map<String, Object> map = null;
		List<MessageType> list = messageTypeService.getAllMessageTypes();
		if (list.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
			map.put("data", list);
		} else {
			map = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
			map.put("message", "No records available");
		}
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PostMapping(consumes="application/json",produces="application/json")
	public ResponseEntity<Object> saveMessageType(@RequestBody MessageType messageType) throws SQLException {
		Map<String, Object> map = null;

		List<String> errors = new ArrayList<String>();
		errors = validatorUtil.validateMessageType(messageType, errors);
		if (errors.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			map.put("errors", errors);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		long id = messageTypeService.saveMessageType(messageType);
		map = HttpStatusMapsConstants.HTTP_STATUS_201_CREATED;
		map.put("message", "Message Type created successfully.");
		Map<String, Object> innerMap = new HashMap<>(1);
		innerMap.put("id", id);
		map.put("data", innerMap);
		return new ResponseEntity<Object>(map, HttpStatus.CREATED);
	}

	@GetMapping(path = "/{id}", produces="application/json")
	public ResponseEntity<Object> getMessageTypeById(@PathVariable("id") String stringId) throws Exception {
		Map<String, Object> map = null;
		List<String> errors = new ArrayList<String>();
		errors = validatorUtil.validateMessageTypeId(stringId, errors);
		if (errors.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			map.put("errors", errors);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		Long id = Long.valueOf(stringId);
		MessageType messageType = messageTypeService.getMessageTypeById(id);
		map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
		map.put("data", messageType);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@DeleteMapping(path = "/{id}", produces="application/json")
	public ResponseEntity<Object> deleteMessageTypeById(@PathVariable("id") String stringId) throws Exception {
		Map<String, Object> map = null;
		List<String> errors = new ArrayList<String>();
		errors = validatorUtil.validateMessageTypeId(stringId, errors);
		if (errors.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			map.put("errors", errors);
			return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
		}

		Long id = Long.valueOf(stringId);
		int rowCount = messageTypeService.deleteMessageTypeById(id);
		map = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
		map.put("message", "Message Type deleted ");
		map.put("data", rowCount);
		return new ResponseEntity<Object>(map, HttpStatus.NO_CONTENT);
	}
}
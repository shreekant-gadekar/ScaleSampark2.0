package com.scalesampark.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scalesampark.domains.Message;
import com.scalesampark.domains.MessageType;
import com.scalesampark.services.MessageService;
import com.scalesampark.services.MessageTypeService;
import com.scalesampark.util.HttpStatusMapsConstants;
import com.scalesampark.validator.ValidatorUtil;

@RestController
@RequestMapping("/messages")
@Validated
public class MessagesController {

	Logger logger = LoggerFactory.getLogger(MessagesController.class);

	@Autowired
	MessageService messageService;

	@Autowired
	MessageTypeService messageTypeService;

	@Autowired
	ValidatorUtil validatorUtil;

	@GetMapping(produces="application/json")
	public ResponseEntity<Object> getAllMessages() {
		Map<String, Object> map = null;
		map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
		map.put("data", messageService.getAllMessages());
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PostMapping(produces="application/json", consumes="application/json")
	public ResponseEntity<Object> saveMessage(@Valid @RequestBody Message message) throws Exception {
		Map<String, Object> map = null;
		List<String> errors = new ArrayList<String>();

		validatorUtil.validateMessage(message, errors);

		if (errors.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			map.put("errors", errors);
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}

		try {
			@SuppressWarnings("unused")
			MessageType messageType = messageTypeService.getMessageTypeById(message.getMessageTypeId());
			long id = messageService.saveMessage(message);
			map = HttpStatusMapsConstants.HTTP_STATUS_201_CREATED;
			map.put("message", "Message created succefully.");

			Map<String, Long> innerMap = new HashMap<>(1);
			innerMap.put("messageUuid", id);

			map.put("data", innerMap);
			return new ResponseEntity<Object>(map, HttpStatus.CREATED);
		} catch (EmptyResultDataAccessException e) {
			System.err.println(e);
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			errors.clear();
			errors.add("Invalid message type id");
			map.put("errors", errors);
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping(path = "/{participantId}", produces="application/json")
	public ResponseEntity<Object> getAllUnseenMessagesByParticipant(
			@PathVariable("participantId") String stringParticipantId) throws Exception {
		Map<String, Object> outerMap = null;
		List<String> errors = new ArrayList<String>();

		validatorUtil.validateParticipantId(stringParticipantId, errors);

		if (errors.size() > 0) {
			outerMap = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			outerMap.put("errors", errors);
			return new ResponseEntity<Object>(outerMap, HttpStatus.BAD_REQUEST);
		}

		Long participantId = Long.valueOf(stringParticipantId);

		Map<String, Object> map = new HashMap<String, Object>(1);
		List<Message> messages = messageService.getAllUnseenMessagesByParticipant(participantId);
		map.put("messages", messages);
		if (messages.size() > 0) {
			outerMap = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
			outerMap.put("data", map);
			return new ResponseEntity<Object>(outerMap, HttpStatus.OK);
		} else {
			outerMap = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
			return new ResponseEntity<Object>(outerMap, HttpStatus.NO_CONTENT);
		}
	}
}
package com.scalesampark.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.scalesampark.domains.Participant;
import com.scalesampark.dto.ParticipantDto;
import com.scalesampark.services.ParticipantService;
import com.scalesampark.util.DateUtil;
import com.scalesampark.util.HttpStatusMapsConstants;
import com.scalesampark.validator.ValidatorUtil;

@RestController
@RequestMapping("/participants")
@Validated
public class ParticipantController {

	Logger logger = LoggerFactory.getLogger(ParticipantController.class);

	@Autowired
	ParticipantService participantService;

	@Autowired
	DateUtil dateUtil;

	@Autowired
	ValidatorUtil validatorUtil;

	@RequestMapping(path = "/{id}",produces="application/json")
	public ResponseEntity<Object> getParticipantById(@PathVariable("id") String stringId) {
		Map<String, Object> map = null;
		List<String> errors = new ArrayList<String>();
		validatorUtil.validateParticipantId(stringId, errors);

		if (errors.size() > 0) {
			map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
			map.put("errors", errors);
			return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
		}

		Long id = Long.valueOf(stringId);

		try {
			Participant participant = participantService.getParticipantById(id);
			if (participant != null) {
				map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
				map.put("data", convertParticipantToParticipantDto(participant));
			}
			return new ResponseEntity<Object>(map, HttpStatus.OK);
		} catch (DataAccessException e) {
			System.err.println("204 >>> \n" + e);
			map = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
			return new ResponseEntity<Object>(map, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			map = HttpStatusMapsConstants.HTTP_STATUS_500_INTERNAL_SERVER_ERROR;
			System.err.println("500 >>> \n" + e);
			return new ResponseEntity<Object>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(produces="application/json")
	public ResponseEntity<Object> getAllParticipants() {
		List<ParticipantDto> participantDtoList = participantService.getAllParticipants().stream().map(this::convertParticipantToParticipantDto)
				.collect(Collectors.toList());
		Map<String, Object> map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
		map.put("data", participantDtoList);
		return new ResponseEntity<Object>(map, HttpStatus.OK);
	}

	@PostMapping(consumes="application/json", produces="application/json")
	public ResponseEntity<Object> saveParticipant(@Valid @RequestBody Participant participant) {
		try {
			if (participantService.isValidParticipant(participant)) {
				if (!participantService.isParticipantAlreadyPresent(participant)) {

					final long id = participantService.saveParticipant(participant);
					Map<String, Object> map = HttpStatusMapsConstants.HTTP_STATUS_201_CREATED;
					Map<String, Object> innerMap = new HashMap<>(1);

					innerMap.put("participantUuid", id);
					map.put("message", "Participant created successfuly.");
					map.put("data", innerMap);

					// logger.info("reposnse =>", map);

					return new ResponseEntity<Object>(map, HttpStatus.CREATED);
				} else {
					Map<String, Object> dataAlreadyPresent = HttpStatusMapsConstants.HTTP_STATUS_409_DATA_ALREADY_PRESENT;
					// logger.info(dataAlreadyPresent.toString());
					return new ResponseEntity<Object>(dataAlreadyPresent, HttpStatus.CONFLICT);
				}
			} else {
				Map<String, Object> noRecords = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
				noRecords.put("message", "No such Employee");
				return new ResponseEntity<Object>(noRecords, HttpStatus.NO_CONTENT);
			}
		} catch (Exception exception) {
			return new ResponseEntity<Object>(HttpStatusMapsConstants.HTTP_STATUS_500_INTERNAL_SERVER_ERROR, HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping(path = "/{id}", produces="application/json")
	public ResponseEntity<Object> deleteParticipant(@PathVariable("id") long id) {
		Map<String, Object> map = HttpStatusMapsConstants.HTTP_STATUS_201_CREATED;
		int rowCount;
		try {
			rowCount = participantService.delete(id);
			map = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
			map.put("message", "Deleted Successfully");
			logger.info(rowCount + "record(s) deleted.");
			return new ResponseEntity<Object>(map, HttpStatus.NO_CONTENT);
		} catch (SQLException e) {
			System.err.println("204 >> \n" + e);
			logger.debug("records not deleted" + e.getMessage());
			map = HttpStatusMapsConstants.HTTP_STATUS_204_NO_CONTENT;
			return new ResponseEntity<Object>(map, HttpStatus.NO_CONTENT);
		}
	}

	public ParticipantDto convertParticipantToParticipantDto(Participant participant) {
		ParticipantDto dto = new ParticipantDto();
		dto.setNickname(participant.getNickname());
		dto.setParticipantUuid("" + participant.getParticipantUuid());
		dto.setLastSeen(participant.getLastSeen().toString());
		return dto;
	}
}
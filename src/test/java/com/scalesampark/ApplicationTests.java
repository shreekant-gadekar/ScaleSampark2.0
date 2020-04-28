package com.scalesampark;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import com.scalesampark.controller.ParticipantController;
import com.scalesampark.domains.Participant;
import com.scalesampark.dto.ParticipantDto;
import com.scalesampark.services.ParticipantService;
import com.scalesampark.util.HttpStatusMapsConstants;

@SpringBootTest
@ContextConfiguration(classes=Application.class)
class ApplicationTests extends ApplicationContextRunner {

	@InjectMocks
	ParticipantController participantController;
	
	@Mock
	ParticipantService participantService;
	
	
	@Test
	public void testGetAllParticipant() {
		Participant participant1 = new Participant(1l, "abcd@gmail.com", "abcnick", Timestamp.valueOf(LocalDateTime.now()), 1l);
		Participant participant2 = new Participant(2l, "xyz@gmail.com", "xyznick", Timestamp.valueOf(LocalDateTime.now()), 2l);
		List<Participant> list = new ArrayList<>();
		list.add(participant1);
		list.add(participant2);
		Map<String, Object> map = HttpStatusMapsConstants.HTTP_STATUS_200_OK;
		List<ParticipantDto> dtoList = list.stream()
        		.map(participantController::convertParticipantToParticipantDto).collect(Collectors.toList());
        map.put("data", dtoList);
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(map, HttpStatus.OK);
		
        when(participantService.getAllParticipants()).thenReturn(list);
//		doReturn(dtoList).when(participantService).getAll();
//		ResponseEntity<Object> resulttoList = participantController.getAll();
//		List<ParticipantDto> resultDtoList = (List<ParticipantDto>) ((Map)resultResponseEntity).get("data");
//        List<ParticipantDto> resultDtoList = participantController.getAll();
//		assertThat(resultDtoList.size()).isEqualTo(1);
//		assertThat(resultDtoList.get(0).getNickname()).isEqualTo("abcnicck");
//		assertThat(resultDtoList.get(0).getNickname()).isEqualTo("abcnicck");
	}
}

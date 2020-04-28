package com.scalesampark.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.scalesampark.domains.Employee;
import com.scalesampark.domains.Participant;
import com.scalesampark.dto.ParticipantDto;



@Component
public class ResponseEntityNodes {
	public Function<ResponseEntity<Object>, List> getList = resultResponseEntity -> {
		return (List)((Map<String,Object>)resultResponseEntity.getBody()).get("data");
	};
	
	public Function<ResponseEntity<Object>, Integer> getStatusCode = resultResponseEntity -> {
		return resultResponseEntity.getStatusCodeValue();
	};
	
	public Function<ResponseEntity<Object>, ParticipantDto> getParticipantDto = resultResponseEntity -> {
		return (ParticipantDto) ((Map<String,Object>)resultResponseEntity.getBody()).get("data");
	};
	
	public Function<ResponseEntity<Object>, Employee> getEmployee = resultResponseEntity -> {
		return (Employee) ((Map<String,Object>)resultResponseEntity.getBody()).get("data");
	};
	
	public Function<ResponseEntity<Object>,HashMap> getHashMap = resultResponseEntity -> {
		return (HashMap) ((Map<String,Object>)resultResponseEntity.getBody()).get("data");
	};
}

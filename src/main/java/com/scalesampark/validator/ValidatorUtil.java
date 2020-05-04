package com.scalesampark.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scalesampark.domains.Employee;
import com.scalesampark.domains.Message;
import com.scalesampark.domains.MessageType;
import com.scalesampark.domains.Participant;
import com.scalesampark.services.EmployeeService;
import com.scalesampark.services.ParticipantService;
import com.scalesampark.util.MessageUtil;

/**
 * ValidatorUtil provides the validation for various classes and fields.
 *
 */
@Component
public class ValidatorUtil {
	
	@Autowired
	MessageUtil messageUtil;
	
	/**
	 * isValidParticipant is to validate that participant is valid or not.
	 * 
	 * @param participant Participant
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean isValidParticipant(Participant participant) throws Exception {
		EmployeeService employeeService = new EmployeeService();
		Employee employees = employeeService.getEmployeeByEmail(participant.getEmail());
		return (employees != null);
	}
	
	/**
	 * isParticipantAlreadyPresent is to check participant is already present in 
	 * the database or not.
	 * 
	 * @param participant
	 * @return
	 */
	public static boolean isParticipantAlreadyPresent(Participant participant) {
		ParticipantService participantService = new ParticipantService();
		Participant participantByEmail = participantService.getParticipantByEmail(participant.getEmail());
		return (participantByEmail != null);
	}
	
	/**
	 * isStringOnlyAlphabet is used to validate the given string value is having 
	 * alphabets only or not and return the boolean value accordingly.
	 *  
	 * @param value String
	 * @return
	 */
	public boolean isStringOnlyAlphabet(String value) 
    { 
        return ((value != null) 
                && (value.matches("^[a-zA-Z]*$"))); 
    }
	
	/**
	 * isNumberOnly is used to validate the given string value is having 
	 * number only or not and return the boolean value accordingly.
	 * 
	 * @param value
	 * @return
	 */
	public boolean isNumberOnly(String value) {
		return ((value != null) 
                && (value.matches("^\\d+$")));
	}
	
	
	/**
	 * isValueNullOrEmpty is used to check given object i.e. 
	 * String / Integer / Long value is null or empty 
	 * or 0 (zero) in case of integer and long value.
	 *    
	 * @param o
	 * @return boolean
	 */
	public boolean isValueNullOrEmpty(Object o) {
		if(o == null)
			return true;
		else
			if(o instanceof String && (((String) o).equalsIgnoreCase("") || ((String) o).equalsIgnoreCase("0")))
				return true;
			else if((o instanceof Integer) && (((Integer) o) == 0))
				return true;
			else if(o instanceof Long && ((Long) o) == 0)
				return true;
			else
				return false;
	}
	
	/**
	 * validateMessage is to validate Message object.
	 * 
	 * @param message
	 * @param errors
	 * @return List<String> errors
	 */
	public List<String> validateMessage(Message message, List<String> errors) {
		
		if(isValueNullOrEmpty(message.getMessageTypeId()))
			errors.add(messageUtil.getMessage("error.common.notblank", "Message Type Id"));
		
		if(isValueNullOrEmpty(message.getParticipantUuid()))
			errors.add(messageUtil.getMessage("error.common.notblank", "Participant Uuid"));
			
		return errors;
	}
	
	/**
	 * validateParticipantId is to validate participant id.
	 * 
	 * @param particiapntId String
	 * @param errors List<String>
	 * @return List<String> errors
	 */
	public List<String> validateParticipantId(String particiapntId, List<String> errors) {
		if(isValueNullOrEmpty(particiapntId))
			errors.add(messageUtil.getMessage("error.common.notblank", "Participant Id"));
		
		if(!isNumberOnly(particiapntId))
			errors.add(messageUtil.getMessage("error.common.numberOnly", "Participant Id"));
		
		return errors;
	}

	/**
	 * validateMessageTypeId is to validate Message Type Id.
	 * 
	 * @param messageTypeId String
	 * @param errors List<String>
	 * @return List<String> errors
	 */
	public List<String> validateMessageTypeId(String messageTypeId, List<String> errors) {
		if(isValueNullOrEmpty(messageTypeId))
			errors.add(messageUtil.getMessage("error.common.notblank", "Message type Id"));
		
		if(!isNumberOnly(messageTypeId))
			errors.add(messageUtil.getMessage("error.common.numberOnly", "Message type Id"));
		
		return errors;
	}
	
	/**
	 * validateMessageType is to validate Message Type object.
	 * 
	 * @param messageType MessageType
	 * @param errors List<String> 
	 * @return List<String> errors
	 */
	public List<String> validateMessageType(MessageType messageType, List<String> errors) {
		if(isValueNullOrEmpty(messageType.getMessageTypeName()))
			errors.add(messageUtil.getMessage("error.messageType.notblank"));
		
		if(!isStringOnlyAlphabet(messageType.getMessageTypeName()))
			errors.add(messageUtil.getMessage("error.common.alphabetsOnly", "Message type name"));
		
		return errors;
	}
	
	/**
	 * validateEmployeeId is to validate Employee Id.
	 * 
	 * @param employeeId String
	 * @param errors List<String>
	 * @return List<String> errors
	 */
	public List<String> validateEmployeeId(String employeeId, List<String> errors) {
		if(isValueNullOrEmpty(employeeId))
			errors.add(messageUtil.getMessage("error.common.notblank", "Employe Id"));
		
		if(!isNumberOnly(employeeId))
			errors.add(messageUtil.getMessage("error.common.numberOnly", "Employe Id"));
		
		return errors;
	}
}
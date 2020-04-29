package com.scalesampark.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.scalesampark.util.HttpStatusMapsConstants;

/**
 * CustomGlobalExceptionHandler class is used to handle the controller level exceptions
 * and return ResponseEntity<Object> object as a response to user.
 *
 */
@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	Logger logger = LoggerFactory.getLogger(CustomGlobalExceptionHandler.class);
    // error handle for @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", Timestamp.valueOf(LocalDateTime.now()));
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }
    
    @ExceptionHandler(value= {DataAccessException.class, Exception.class})
    public ResponseEntity<Object> dataAccessException(RuntimeException ex, WebRequest request) throws IOException {
    	List<String> errors = new ArrayList<String>();
    	logger.debug("400 >>> \n" + ex.getMessage());
		
    	if(ex.getClass().getSimpleName().contains("EmptyResult"))
    		errors.add("Data not found for specified details.");
    	else
    		errors.add("Something went wrong.");
    	
    	Map<String, Object> map = null;
    	map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
		map.put("errors", errors);
		return handleExceptionInternal(ex, map, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> sqlException(RuntimeException ex, WebRequest request) throws IOException {
    	logger.debug("400 >>> \n" + ex.getMessage());
		List<String> errors = new ArrayList<String>();
    	errors.add("Data operation failed.");
    	Map<String, Object> map = null;
		map = HttpStatusMapsConstants.HTTP_STATUS_400_BAD_REQUEST;
		map.put("errors", errors);
		return handleExceptionInternal(ex, map, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }

}

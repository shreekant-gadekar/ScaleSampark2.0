package com.scalesampark.services;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * EmployeeService class is working as a mediater between EmployeeController and
 * EmployeeDao.
 * 
 * It fetches the records from database using EmployeeDao and pass it 
 * to EmployeeController. 
 *
 */
@Profile("test")
@Configuration
public class EmployeeTestServiceConfig {

	Logger logger = LoggerFactory.getLogger(EmployeeTestServiceConfig.class);
	
	@Bean
	@Primary
	public EmployeeService employeeService() {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>     In EmployeeTestService     <<<<<<<<<<<<<<<<<<<<<<<<<");
		return Mockito.mock(EmployeeService.class);
	}
}
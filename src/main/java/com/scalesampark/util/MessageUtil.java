package com.scalesampark.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class MessageUtil {

	@Autowired
	MessageSource messageSource;

	public String getMessage(String propertyName) {
		return messageSource.getMessage(propertyName, null, Locale.US);
	}
	
	public String getMessage(String propertyName, Object... objects) {
		return messageSource.getMessage(propertyName, objects, Locale.US);
	}
}

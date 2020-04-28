package com.scalesampark.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * RegexValidator is used as a validator for regex annotated field.
 *
 */
public class RegexValidator implements ConstraintValidator<Regex, String> {
	private String regex;
	
	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	/**
	 * This method is used to validate the field value with given regex.
	 * 
	 * @param fieldValue String
	 * @return boolean
	 */
	public boolean isStringMatchingWithRegex(String fieldValue) 
    { 
        return ((fieldValue != null) 
                && (fieldValue.matches(regex))); 
    }

	/**
	 * to initialize the regex value passed with annotation.
	 */
	@Override
	public void initialize(Regex regex) {
		this.regex = regex.value();
	}
	
	/**
	 * overridden method to validate the field as per the regex given.
	 */
	@Override
	public boolean isValid(String field, ConstraintValidatorContext context) {
		return isStringMatchingWithRegex(field);
	} 
}

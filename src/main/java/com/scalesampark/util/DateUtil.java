package com.scalesampark.util;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

/**
 * DateUtil is used to have the date utilities as per reqirement.
 *
 */
@Component
public class DateUtil {
	/**
	 * getCurrentDateTime give the local date time in timestamp 
	 * format to save it to database.
	 * 
	 * @return Timestamp
	 */
	public Timestamp getCurrentDateTime() {
		LocalDateTime ldt = LocalDateTime.now();
		Timestamp timestamp = Timestamp.valueOf(ldt);
		return timestamp;
	}
}

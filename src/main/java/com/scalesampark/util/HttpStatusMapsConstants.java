package com.scalesampark.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * HttpStatusMapsConstants class contains the pre-defined template responses
 * which will be used to create fast responses to send back to user by 
 * changing only 1 or 2 properties.
 *
 */
public class HttpStatusMapsConstants {
	/**
	 * For representing HttpStatus OK response.
	 */
	public static 
		Map<String, Object> HTTP_STATUS_200_OK = new LinkedHashMap<String, Object>() {{	
			put("status", 200);
			put("message", "Successful");
			put("data", null);
			put("errors", null);
		}};
		
	/**
	 * For representing HttpStatus CREATED response.
	 */
		public static 
		Map<String, Object> HTTP_STATUS_201_CREATED = new LinkedHashMap<String, Object>() {{	
			put("status", 201);
			put("message", "Created successfuly.");
			put("data", null);
			put("errors", null);
		}};
	
	/**
	 * For representing HttpStatus NO_CONTENT response.
	 */
	public static 
		Map<String, Object> HTTP_STATUS_204_NO_CONTENT = new LinkedHashMap<String, Object>() {{	
			put("status", 204);
			put("message", "No Content.");
			put("data", null);
			put("errors", null);
		}};
	
	/**
	 * For representing HttpStatus BAD_REQUEST response.
	 */
	public static 
		Map<String, Object> HTTP_STATUS_400_BAD_REQUEST = new LinkedHashMap<String, Object>() {{	
			put("status", 400);
			put("message", "Bad Request");
			put("data", null);
			put("errors", null);
		}};
	
	
	/**
	 * For representing HttpStatus DATA_ALREADY_PRESENT response.
	 */
	public static 
		Map<String, Object> HTTP_STATUS_409_DATA_ALREADY_PRESENT = new LinkedHashMap<String, Object>() {{	
			put("status", 409);
			put("message", "Data Already Present");
			put("data", null);
			put("errors", null);
		}};
		
	
	/**
	 * For representing HttpStatus INTERNAL_SERVER_ERROR response.
	 */
	public static 
		Map<String, Object> HTTP_STATUS_500_INTERNAL_SERVER_ERROR = new LinkedHashMap<String, Object>() {{	
			put("status", 500);
			put("message", "Internal Server Error");
			put("data", null);
			put("errors", null);
		}};
}
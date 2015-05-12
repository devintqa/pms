package com.psk.pms.validator;

public class BaseValidator {
	
	 public static final String MOBILE_PATTERN = "[0-9]{10}";
	 public static final String NAME_PATTERN = "^[a-zA-Z]{2,20}$";
	 public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"  
		   + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	 String ID_PATTERN = "[0-9]+"; 
	 String AMOUNT_PATTERN = "\\d+(?:\\.\\d+)?$";

}

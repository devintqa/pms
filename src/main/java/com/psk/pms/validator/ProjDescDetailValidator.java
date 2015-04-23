package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.psk.pms.model.ProjDescDetail;

public class ProjDescDetailValidator implements Validator{
 
	@Override
	public boolean supports(Class<?> clazz) {
		return ProjDescDetail.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
		
	}
	
	
}


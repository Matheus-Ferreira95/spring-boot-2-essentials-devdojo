package com.matheusf.springess.controller.exception;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class ValidationError extends StandardError {
		
	@Singular
	private List<FieldMessage> errors;
	
	public void addError(FieldMessage fieldMessage) { 
		errors.add(fieldMessage);
	}
	
	public void initializeList() {
		errors = new ArrayList<>();
	}
	
}

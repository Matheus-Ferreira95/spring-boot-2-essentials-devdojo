package com.matheusf.springess.controller.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.matheusf.springess.exception.BadRequestException;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardError> badRequest(BadRequestException e) {
		StandardError see = StandardError.builder()
							.timestamp(LocalDateTime.now())
							.status(HttpStatus.BAD_REQUEST.value())
							.title("Bad Request Exception, Check the Documentation")
							.details(e.getMessage())
							.developerMessage(e.getClass().getName())
							.build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(see);
	}	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationError(MethodArgumentNotValidException e) {
		ValidationError see = ValidationError.builder()
							.timestamp(LocalDateTime.now())
							.status(HttpStatus.BAD_REQUEST.value())
							.title("Bad Request Exception, Check the Documentation")
							.details("Check the errors")
							.developerMessage(e.getClass().getName())
							.build();	
		
		see.initializeList();			
		for (FieldError x : e.getBindingResult().getFieldErrors()) {				
			see.addError(FieldMessage.builder()
					.field(x.getField())
					.message(x.getDefaultMessage())
					.build());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(see);							
	}	
}

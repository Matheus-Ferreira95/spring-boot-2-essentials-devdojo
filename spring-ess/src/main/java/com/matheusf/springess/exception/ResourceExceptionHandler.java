package com.matheusf.springess.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.matheusf.springess.controller.exception.StandardError;

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
	
}

package com.matheusf.springess.controller.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.matheusf.springess.exception.BadRequestException;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardError> handleBadRequest(BadRequestException e) {
		StandardError see = StandardError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).title("Bad Request Exception, Check the Documentation")
				.details(e.getMessage()).developerMessage(e.getClass().getName()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(see);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ValidationError see = ValidationError.builder()
				.timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value())
				.title("Bad Request Exception, Check the Documentation")
				.details("Check the errors")
				.developerMessage(ex.getClass().getName())
				.build();

		see.initializeList();
		for (FieldError x : ex.getBindingResult().getFieldErrors()) {
			see.addError(FieldMessage.builder().field(x.getField()).message(x.getDefaultMessage()).build());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(see);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		StandardError see = StandardError.builder()
			.timestamp(LocalDateTime.now())
			.status(status.value())
			.title(ex.getCause().getMessage())
			.details(ex.getMessage())
			.developerMessage(ex.getClass().getName())
			.build();

		return new ResponseEntity<>(see, headers, status);
	}
}

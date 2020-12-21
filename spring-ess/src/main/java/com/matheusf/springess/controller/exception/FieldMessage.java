package com.matheusf.springess.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FieldMessage {
	
	private final String field;
	private final String message;	
	
}

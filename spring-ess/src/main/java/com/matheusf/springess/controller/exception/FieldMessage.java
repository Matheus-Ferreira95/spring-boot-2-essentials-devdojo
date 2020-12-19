package com.matheusf.springess.controller.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FieldMessage {
	
	private final String field;
	private final String message;	
}

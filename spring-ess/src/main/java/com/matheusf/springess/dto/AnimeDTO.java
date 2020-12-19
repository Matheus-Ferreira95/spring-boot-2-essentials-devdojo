package com.matheusf.springess.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class AnimeDTO {	
	@NotEmpty(message = "The anime name cannot be empty")	
	private String name;
	
}

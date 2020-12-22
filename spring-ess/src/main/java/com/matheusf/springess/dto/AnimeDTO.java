package com.matheusf.springess.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimeDTO {	
	private Long id;
	@NotEmpty(message = "The anime name cannot be empty")	
	private String name;
	
}

package com.matheusf.curse2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Anime {
	
	private Long id;
	private String name;	
	
	public void kk() {
		Anime anime = new Anime(5L, "mity");
	}
	
}

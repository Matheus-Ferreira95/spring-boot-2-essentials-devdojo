package com.matheusf.springess.util;

import com.matheusf.springess.dto.AnimeDTO;

public class AnimeDTOCreator {
	
	public static AnimeDTO createAnimeDTO() {
		return AnimeDTO.builder()
				.name(AnimeCreator.createAnimeToBeSaved().getName())
				.build();
	}
	
}

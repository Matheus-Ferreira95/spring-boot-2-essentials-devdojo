
package com.matheusf.springess.util;

import com.matheusf.springess.domain.Anime;

public class AnimeCreator {
	
	public static Anime createAnimeToBeSaved() {
		return Anime.builder()
				.name("Hajime no Ippo")
				.build();
	}
	
	public static Anime createValidAnime() {
		return Anime.builder()
				.id(1L)
				.name("Hajime no Ippo")
				.build();
	}	
	
	public static Anime createValidUpdatedAnime() {
		return Anime.builder()
				.id(1L)
				.name("Hajime no Ippo 2")
				.build();
	}		
	
	public static Anime createAnimeNotExistsOnDatabase() {
		return Anime.builder()
				.id(60L)
				.name("you don't found me")
				.build();
	}
}

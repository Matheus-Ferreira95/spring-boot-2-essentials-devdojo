package com.matheusf.springess;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.matheusf.springess.domain.Anime;

@SpringBootApplication
public class SpringEssApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringEssApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Anime anime = new Anime(5L, "Pokemon");
		Anime anime2 = Anime.builder().id(5L).name("Dragon ball z").build();
		
		System.out.println(anime.getId().equals(anime2.getId()));
		
	}

}

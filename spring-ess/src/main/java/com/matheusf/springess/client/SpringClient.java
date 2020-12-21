package com.matheusf.springess.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.matheusf.springess.domain.Anime;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class SpringClient implements CommandLineRunner {	
		
	@Override
	public void run(String... args) throws Exception {		
		
		ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 1);
		log.info(entity);		
						
		Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 1);
		log.info(object);		
		
		Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
		log.info(Arrays.toString(animes));
		
		ResponseEntity<List<Anime>> listAnimes = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {} );
		log.info(listAnimes.getBody());		
		
		/*
		Anime kingdom = new Anime(null, "coragem o cao covarde");
		Anime kingdomSaved = new RestTemplate().postForObject("http://localhost:8080/animes", kingdom, Anime.class);
		log.info(kingdomSaved);
		*/
		
		Anime yugioh = Anime.builder().name("yugioh").build();
		ResponseEntity<Anime> kaiba = new RestTemplate().exchange("http://localhost:8080/animes", HttpMethod.POST, new HttpEntity<>(yugioh, createJsonHeader()),
				new ParameterizedTypeReference<Anime>() {
				});
		
	}	
	
	private static HttpHeaders createJsonHeader() {
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		return header;
	}
	
}

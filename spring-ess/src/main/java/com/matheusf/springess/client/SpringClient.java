package com.matheusf.springess.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
		
		ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {} );
		log.info(exchange.getBody());
			
		
				
				
		
	}	
	
}

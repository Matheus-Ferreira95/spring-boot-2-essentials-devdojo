package com.matheusf.springess.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.service.AnimeService;
import com.matheusf.springess.util.DateUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping(value = "animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

	private final DateUtil dateUtil;
	private final AnimeService animalService;
	
	@GetMapping
	public ResponseEntity<List<Anime>> list(){
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok().body(animalService.listAll());
	}	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok().body(animalService.findById(id));
	
	}
}

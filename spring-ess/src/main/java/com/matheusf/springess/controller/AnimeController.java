package com.matheusf.springess.controller;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.dto.AnimeDTO;
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
	private final AnimeService animeService;
	
	@GetMapping
	public ResponseEntity<List<Anime>> list(){
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok().body(animeService.listAll());
	}	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){
		log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
		return ResponseEntity.ok().body(animeService.findByIdOrThrowBadRequestException(id));	
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<Anime>> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
		return ResponseEntity.ok().body(animeService.findByName(name));
	}
	
	@PostMapping
	public ResponseEntity<Anime> save(@RequestBody AnimeDTO animeDTO){
		Anime anime = animeService.save(animeDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(anime.getId()).toUri();
		return ResponseEntity.created(uri).body(anime);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		animeService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(@RequestBody AnimeDTO animeDTO, @PathVariable long id) {
		animeService.update(animeDTO, id);
		return ResponseEntity.noContent().build();
	}
	
}

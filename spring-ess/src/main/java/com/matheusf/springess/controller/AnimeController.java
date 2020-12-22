package com.matheusf.springess.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "animes")
@RequiredArgsConstructor
public class AnimeController {
	
	private final AnimeService animeService;
	
	@GetMapping
	public ResponseEntity<Page<Anime>> list(Pageable pageable){		
		return ResponseEntity.ok().body(animeService.listAll(pageable));
	}	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Anime> findById(@PathVariable long id){		
		return ResponseEntity.ok().body(animeService.findByIdOrThrowBadRequestException(id));	
	}
	
	@GetMapping(value = "/all")
	public ResponseEntity<List<Anime>> findAll() {
		return ResponseEntity.ok(animeService.listAllNonPageable());
	}
	
	@GetMapping(value = "/find")
	public ResponseEntity<List<Anime>> findByName(@RequestParam(value = "name", defaultValue = "") String name) {
		return ResponseEntity.ok().body(animeService.findByName(name));
	}
	
	@PostMapping
	public ResponseEntity<Anime> save(@Valid @RequestBody AnimeDTO animeDTO){
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

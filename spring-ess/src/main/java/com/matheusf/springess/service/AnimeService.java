package com.matheusf.springess.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.dto.AnimeDTO;
import com.matheusf.springess.repository.AnimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimeService {
	
	private final AnimeRepository animeRepository;
	
	public List<Anime> listAll() {		
		return animeRepository.findAll();
	}

	public Anime findByIdOrThrowBadRequestException(long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Anime not Found"));
	}	
	
	public Anime save(AnimeDTO animeDTO) {
		return animeRepository.save(Anime.builder().name(animeDTO.getName()).build());
	}

	public void delete(long id) {		
		animeRepository.delete(findByIdOrThrowBadRequestException(id));
	}
	
	public void update(AnimeDTO animeDTO, long id) {
		Anime anime = findByIdOrThrowBadRequestException(id);
		anime.setName(animeDTO.getName());
		animeRepository.save(anime);
	}
	
}

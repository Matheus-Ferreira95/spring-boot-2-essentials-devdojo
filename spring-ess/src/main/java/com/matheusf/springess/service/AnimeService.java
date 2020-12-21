package com.matheusf.springess.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.dto.AnimeDTO;
import com.matheusf.springess.exception.BadRequestException;
import com.matheusf.springess.mapper.AnimeMapper;
import com.matheusf.springess.repository.AnimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AnimeService {
	
	private final AnimeRepository animeRepository;
	

	public List<Anime> listAllNonPageable() {
		return animeRepository.findAll();
	}
	
	public Page<Anime> listAll(Pageable pageable) {		
		return animeRepository.findAll(pageable);
	}

	public Anime findByIdOrThrowBadRequestException(long id) {
		return animeRepository.findById(id)
				.orElseThrow(() -> new BadRequestException("Anime not Found"));
	}	
	
	public List<Anime> findByName(String name) {
		return animeRepository.findByName(name);
	}
	
	@Transactional
	public Anime save(AnimeDTO animeDTO) {		
		//return animeRepository.save(Anime.builder().name(animeDTO.getName()).build());
		return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animeDTO));
	}

	public void delete(long id) {		
		animeRepository.delete(findByIdOrThrowBadRequestException(id));		
	}
	
	@Transactional
	public void update(AnimeDTO animeDTO, long id) {
		Anime anime = findByIdOrThrowBadRequestException(id);
		anime = AnimeMapper.INSTANCE.toAnime(animeDTO);
		anime.setId(id);
		animeRepository.save(anime);		
	}	
}

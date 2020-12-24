package com.matheusf.springess.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.dto.AnimeDTO;
import com.matheusf.springess.service.AnimeService;
import com.matheusf.springess.util.AnimeCreator;
import com.matheusf.springess.util.AnimeDTOCreator;

@ExtendWith(SpringExtension.class)
public class AnimeControllerTest {
	
	@InjectMocks
	private AnimeController animeController;
	@Mock
	private AnimeService animeServiceMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
		.thenReturn(animePage);
		
		BDDMockito.when(animeServiceMock.listAllNonPageable())
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
		.thenReturn(AnimeCreator.createValidAnime());		
		
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimeDTO.class)))
		.thenReturn(AnimeCreator.createValidAnime());	
		
		BDDMockito.doNothing().when(animeServiceMock).update(ArgumentMatchers.any(AnimeDTO.class), ArgumentMatchers.anyLong());
		
		BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
	}	
	
	@Test
	@DisplayName("List returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		Page<Anime> animePage = animeController.list(null).getBody();
		
		assertThat(animePage).isNotNull();
		
		assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		
		assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);		
	}	
	
	@Test
	@DisplayName("Findall returns list of anime when successful")
	void findAll_ReturnsListOfAnimes_WhenSuccessful() {
		Anime expectedAnime = AnimeCreator.createValidAnime();
		
		List<Anime> animes = animeController.findAll().getBody();
				
		assertThat(animes)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.contains(expectedAnime);		
	}	
	
	@Test
	@DisplayName("findById returns anime when successful")
	void findById_ReturnsAnimes_WhenSuccessful() {
		Anime expectedAnime = AnimeCreator.createValidAnime();
		
		Anime anime = animeController.findById(expectedAnime.getId()).getBody();
					
		
		assertThat(anime)
			.isNotNull()
			.isEqualTo(expectedAnime);				
	}	
	
	@Test
	@DisplayName("findByName returns anime list when successful")
	void findByName_ReturnsAnimesList_WhenSuccessful() {
		Anime expectedAnime = AnimeCreator.createValidAnime();
		
		List<Anime> animes = animeController.findByName(expectedAnime.getName()).getBody();
				
		assertThat(animes)
			.isNotEmpty()
			.hasSize(1);
		
		assertThat(animes.get(0).getName()).isEqualTo(expectedAnime.getName());						
	}	
	
	@Test
	@DisplayName("findByName returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
			
		List<Anime> animes = animeController.findByName("anime").getBody();
				
		assertThat(animes)
			.isNotNull()
			.isEmpty();					
	}	
	
	@Test
	@DisplayName("save returns anime when successful")
	void save_ReturnsAnimes_WhenSuccessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		
		Anime anime = animeController.save(AnimeDTOCreator.createAnimeDTO()).getBody();
				
		assertThat(anime.getId())
			.isNotNull()
			.isEqualTo(expectedId);		
	}
	
	@Test
	@DisplayName("updated changes anime name when successful") // void não tem muito oque testar sem testes de integração com o bc
	void updated_changesAnimeName_WhenSuccessful() {
		
		assertThatCode(() -> animeController.update(AnimeDTOCreator.createAnimeDTO(), AnimeCreator.createValidAnime().getId()))
		.doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = animeController.update(AnimeDTOCreator.createAnimeDTO(), AnimeCreator.createValidAnime().getId());
		
		assertThat(entity).isNotNull();
		
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);		
	}	
	
	@Test
	@DisplayName("delete removes when successful") // void não tem muito oque testar sem testes de integração com o bc
	void delete_RemovesAnime_WhenSuccessful() {
		
		assertThatCode(() -> animeController.delete(1L))
		.doesNotThrowAnyException();
		
		ResponseEntity<Void> entity = animeController.delete(1L);
		
		assertThat(entity).isNotNull();
		
		assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);		
	}	
}

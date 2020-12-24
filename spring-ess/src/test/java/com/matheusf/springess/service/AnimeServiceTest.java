package com.matheusf.springess.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.exception.BadRequestException;
import com.matheusf.springess.repository.AnimeRepository;
import com.matheusf.springess.util.AnimeCreator;
import com.matheusf.springess.util.AnimeDTOCreator;

@ExtendWith(SpringExtension.class)
public class AnimeServiceTest {
	
	@InjectMocks
	private AnimeService animeService;
	@Mock
	private AnimeRepository animeRepositoryMock;
	
	@BeforeEach
	void setUp() {
		PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));		
		
		BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(Pageable.class)))
		.thenReturn(animePage);
		
		BDDMockito.when(animeRepositoryMock.findAll())
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.of(AnimeCreator.createValidAnime()));		
		
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(List.of(AnimeCreator.createValidAnime()));
		
		BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
		.thenReturn(AnimeCreator.createValidAnime());	
				
		BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
	}	
	
	@Test
	@DisplayName("ListAll returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
		String expectedName = AnimeCreator.createValidAnime().getName();
		
		Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));
		
		assertThat(animePage).isNotNull();
		
		assertThat(animePage.toList())
			.isNotEmpty()
			.hasSize(1);
		
		assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);		
	}	
	
	@Test
	@DisplayName("listAllNonPageable returns list of anime when successful")
	void listAllNonPageable_ReturnsListOfAnimes_WhenSuccessful() {
		Anime expectedAnime = AnimeCreator.createValidAnime();
		
		List<Anime> animes = animeService.listAllNonPageable();
				
		assertThat(animes)
			.isNotNull()
			.isNotEmpty()
			.hasSize(1)
			.contains(expectedAnime);		
	}	
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException returns anime when successful")
	void findByIdOrThrowBadRequestException_ReturnsAnimes_WhenSuccessful() {
		Anime expectedAnime = AnimeCreator.createValidAnime();
		
		Anime anime = animeService.findByIdOrThrowBadRequestException(expectedAnime.getId());
		
		assertThat(anime).isNotNull();
		
		assertThat(anime)
			.isNotNull()
			.isEqualTo(expectedAnime);				
	}	
	
	@Test
	@DisplayName("findByIdOrThrowBadRequestException throws badrequestException when Anime is not found")
	void findByIdOrThrowBadRequestException_throwsBadRequest_WhenAnimeNotFound() {
		BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
		.thenReturn(Optional.empty());		
		
		assertThatThrownBy(() -> animeService.findByIdOrThrowBadRequestException(1L))
		.isInstanceOf(BadRequestException.class);		
	}	
	
	@Test
	@DisplayName("findByName returns anime list when successful")
	void findByName_ReturnsAnimesList_WhenSuccessful() {
		Anime expectedAnime = AnimeCreator.createValidAnime();
		
		List<Anime> animes = animeService.findByName(expectedAnime.getName());
				
		assertThat(animes)
			.isNotEmpty()
			.hasSize(1);
		
		assertThat(animes.get(0).getName()).isEqualTo(expectedAnime.getName());						
	}	
	
	@Test
	@DisplayName("findByName returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
		.thenReturn(Collections.emptyList());
			
		List<Anime> animes = animeService.findByName("anime");
				
		assertThat(animes)
			.isNotNull()
			.isEmpty();					
	}	
	
	@Test
	@DisplayName("save returns anime when successful")
	void save_ReturnsAnimes_WhenSuccessful() {
		Long expectedId = AnimeCreator.createValidAnime().getId();
		
		Anime anime = animeService.save(AnimeDTOCreator.createAnimeDTO());
				
		assertThat(anime.getId())
			.isNotNull()
			.isEqualTo(expectedId);		
	}
	
	@Test
	@DisplayName("updated changes anime name when successful") // void não tem muito oque testa sem testes de integração com o bc
	void updated_changesAnimeName_WhenSuccessful() {
		
		assertThatCode(() -> animeService.update(AnimeDTOCreator.createAnimeDTO(), AnimeCreator.createValidAnime().getId()))
		.doesNotThrowAnyException();			
	}	
	
	@Test
	@DisplayName("delete removes when successful") // void não tem muito oque testa sem testes de integração com o bc
	void delete_RemovesAnime_WhenSuccessful() {
		
		assertThatCode(() -> animeService.delete(1L))
		.doesNotThrowAnyException();		
	}	
}

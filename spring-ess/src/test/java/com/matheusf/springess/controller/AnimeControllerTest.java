package com.matheusf.springess.controller;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.service.AnimeService;
import com.matheusf.springess.util.AnimeCreator;

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
}

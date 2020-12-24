package com.matheusf.springess.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.dto.AnimeDTO;
import com.matheusf.springess.repository.AnimeRepository;
import com.matheusf.springess.util.AnimeCreator;
import com.matheusf.springess.util.AnimeDTOCreator;
import com.matheusf.springess.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AnimeControllerIT {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@LocalServerPort
	private int port;

	@Autowired
	private AnimeRepository animeRepository;

	@Test
	@DisplayName("list returns list of anime inside page object when successful")
	void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

		String expectedName = savedAnime.getName();

		PageableResponse<Anime> animePage = testRestTemplate
				.exchange("/animes", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<Anime>>() {
				}).getBody();

		assertThat(animePage).isNotNull();

		assertThat(animePage.toList()).isNotEmpty().hasSize(1);

		assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("listAll returns list of anime when successful")
	void listAll_ReturnsListOfAnimes_WhenSuccessful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

		String expectedName = savedAnime.getName();

		List<Anime> animes = testRestTemplate
				.exchange("/animes/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animes).isNotNull().isNotEmpty().hasSize(1);

		assertThat(animes.get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("findById returns anime when successful")
	void findById_ReturnsAnimes_WhenSuccessful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

		Long expectedId = savedAnime.getId();

		Anime anime = testRestTemplate.getForObject("/animes/{id}", Anime.class, expectedId);

		assertThat(anime).isNotNull().isEqualTo(savedAnime);
	}

	@Test
	@DisplayName("findByName returns anime list when successful")
	void findByName_ReturnsAnimesList_WhenSuccessful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

		String expectedName = savedAnime.getName();

		String url = String.format("/animes/find?name=%s", expectedName);
		List<Anime> animes = testRestTemplate
				.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animes).isNotEmpty().hasSize(1);

		assertThat(animes.get(0).getName()).isEqualTo(expectedName);
	}

	@Test
	@DisplayName("findByName returns an empty list of anime when anime is not found")
	void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {
		List<Anime> animes = testRestTemplate
				.exchange("/animes/find?name=dbz", HttpMethod.GET, null, new ParameterizedTypeReference<List<Anime>>() {
				}).getBody();

		assertThat(animes).isEmpty();
	}

	@Test
	@DisplayName("save returns anime when successful")
	void save_ReturnsAnimes_WhenSuccessful() {
		AnimeDTO animeDTO = AnimeDTOCreator.createAnimeDTO();

		ResponseEntity<Anime> anime = testRestTemplate.postForEntity("/animes", animeDTO, Anime.class);

		assertThat(anime).isNotNull();
		assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(anime.getBody()).isNotNull();
		assertThat(anime.getBody().getId()).isNotNull();
	}

	@Test
	@DisplayName("updated changes anime name when successful")
	void updated_changesAnimeName_WhenSuccessful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

		AnimeDTO animeDTO = AnimeDTOCreator.createAnimeDTO();
		animeDTO.setName("new name");

		ResponseEntity<Void> anime = testRestTemplate.exchange("/animes/{id}", HttpMethod.PUT,
				new HttpEntity<>(animeDTO), Void.class, savedAnime.getId());

		assertThat(anime).isNotNull();
		assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	@DisplayName("delete removes when successful")
	void delete_RemovesAnime_WhenSuccessful() {
		Anime savedAnime = animeRepository.save(AnimeCreator.createAnimeToBeSaved());

		ResponseEntity<Void> anime = testRestTemplate.exchange("/animes/{id}", HttpMethod.DELETE,
				null, Void.class, savedAnime.getId());

		assertThat(anime).isNotNull();
		assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
package com.matheusf.springess;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.repository.AnimeRepository;

@DataJpaTest
@DisplayName("Tests for Anime Repository")
class AnimeRepositoryTest {
	
	@Autowired
	private AnimeRepository animeRepository;	
	
	@Test
	@DisplayName("Save persists anime when Sucessful")
	void save_PersistAnime_WhenSuccessful() {
		Anime animeToBeSaved = createAnime();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
		
		animeSaved.setName("A vaca e o frango");
		
		Anime animeUpdated = this.animeRepository.save(animeSaved);
		
		assertThat(animeSaved).isNotNull();
		
		assertThat(animeSaved.getId()).isNotNull();
		
		assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());		
	}
	
	@Test
	@DisplayName("Save updates anime when Sucessful")
	void save_UpdatesAnime_WhenSuccessful() {
		Anime animeToBeSaved = createAnime();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
				
		assertThat(animeSaved).isNotNull();
		
		assertThat(animeSaved.getId()).isNotNull();
		
		assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());		
	}
	
	@Test
	@DisplayName("Delete removes anime when Sucessful")
	void delete_RemovesAnime_WhenSuccessful() {
		Anime animeToBeSaved = createAnime();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
				
		this.animeRepository.delete(animeSaved);
		
		Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());
		
		assertThat(animeOptional).isEmpty();
	}
	
	@Test
	@DisplayName("Find By Name returns list of anime when Sucessful")
	void findByName_ReturnsListOfAnime_WhenSuccessful() {
		Anime animeToBeSaved = createAnime();
		
		Anime animeSaved = this.animeRepository.save(animeToBeSaved);
				
		String name = animeSaved.getName();
		
		List<Anime> animes = this.animeRepository.findByName(name);
		
		assertThat(animes)
			.isNotEmpty()
			.contains(animeSaved);		
	}
	
	@Test
	@DisplayName("Find By Name returns empty list when no anime is found")
	void findByName_ReturnsEmptList_WhenAnimeIsNotFound() {							
		List<Anime> animes = this.animeRepository.findByName("usahuash");
		
		assertThat(animes).isEmpty();
	}
	
	@Test
	@DisplayName("Save throw ConstraintViolationException when name is empty")
	void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
		Anime anime = new Anime();
		
		/*
		assertThatThrownBy(() -> this.animeRepository.save(anime))
			.isInstanceOf(ConstraintViolationException.class);
		*/
		
		assertThatExceptionOfType(ConstraintViolationException.class)
		.isThrownBy(() -> this.animeRepository.save(anime))
		.withMessageContaining("The anime name cannot be empty");
			
	}
	
	
	private Anime createAnime() {
		return Anime.builder()
				.name("Hajime no Ippo")
				.build();
	}	
}

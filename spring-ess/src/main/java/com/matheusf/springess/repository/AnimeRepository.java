package com.matheusf.springess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matheusf.springess.domain.Anime;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, Long> {
		
}

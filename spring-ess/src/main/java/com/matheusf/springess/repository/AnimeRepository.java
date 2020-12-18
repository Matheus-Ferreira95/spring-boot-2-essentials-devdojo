package com.matheusf.springess.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.matheusf.springess.domain.Anime;

@Repository
public interface AnimeRepository {
	
	List<Anime> listAll();	
}

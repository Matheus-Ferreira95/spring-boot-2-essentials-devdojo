package com.matheusf.springess.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.matheusf.springess.domain.Anime;
import com.matheusf.springess.dto.AnimeDTO;

@Mapper(componentModel = "spring") // possibilita fazer a ijd caso preciso
public abstract class AnimeMapper {
	
	public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);
		
	public abstract Anime toAnime(AnimeDTO animeDto);
}

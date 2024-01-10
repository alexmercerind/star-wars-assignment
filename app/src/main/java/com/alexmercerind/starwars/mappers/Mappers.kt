package com.alexmercerind.starwars.mappers

import com.alexmercerind.starwars.api.dto.CharacterDTO
import com.alexmercerind.starwars.api.dto.FilmDTO
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.model.Film

fun CharacterDTO.toCharacter(page: Int) = Character(
    birthYear,
    created,
    edited,
    eyeColor,
    films,
    gender,
    hairColor,
    height,
    homeworld,
    mass,
    name,
    skinColor,
    species,
    starships,
    url,
    vehicles,
    page
)

fun FilmDTO.toFilm() = Film(
    characters,
    created,
    director,
    edited,
    episodeId,
    openingCrawl,
    planets,
    producer,
    releaseDate,
    species,
    starships,
    title,
    url,
    vehicles
)

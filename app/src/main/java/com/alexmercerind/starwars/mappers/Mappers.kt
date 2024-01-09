package com.alexmercerind.starwars.mappers

import com.alexmercerind.starwars.api.dto.CharacterDTO
import com.alexmercerind.starwars.model.Character

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

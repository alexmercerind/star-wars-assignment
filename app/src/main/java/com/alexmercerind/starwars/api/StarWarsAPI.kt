package com.alexmercerind.starwars.api

import com.alexmercerind.starwars.api.dto.CharacterPageDTO
import com.alexmercerind.starwars.api.dto.FilmDTO
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface StarWarsAPI {
    @Headers("Content-Type: application/json")
    @GET("people")
    suspend fun getCharacterPage(@Query("page") page: Int): CharacterPageDTO

    @Headers("Content-Type: application/json")
    @GET("films/{id}")
    suspend fun getFilm(@Path("id") id: Int): FilmDTO
}

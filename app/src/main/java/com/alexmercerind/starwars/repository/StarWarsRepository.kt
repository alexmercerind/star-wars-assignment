package com.alexmercerind.starwars.repository

import android.app.Application
import com.alexmercerind.starwars.api.StarWarsService
import com.alexmercerind.starwars.db.StarWarsDatabase
import com.alexmercerind.starwars.mappers.toCharacter
import com.alexmercerind.starwars.mappers.toFilm
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.model.Film
import com.alexmercerind.starwars.utils.Result
import retrofit2.HttpException
import java.io.IOError

class StarWarsRepository(application: Application) {
    private val database = StarWarsDatabase(application)

    suspend fun getCharacters(page: Int): Result<List<Character>> {
        try {


            // Query from cache.
            val cache = database.characterDao().getCharacterPage(page)
            if (cache.isNotEmpty()) {
                return Result.Success(cache)
            }

            // Fresh request.
            val response = StarWarsService.api.getCharacterPage(page)
            val values = response.results.map { it.toCharacter(page) }
            // Insert into cache.
            database.characterDao().insert(values)
            return Result.Success(values)


        } catch (e: HttpException) {
            // NOTE:
            // Special case where status code is 404 if page limit is reached.
            // Thus, return empty List<Character>.
            if (e.code() == 404) {
                return Result.Success(listOf())
            }
            return Result.Error(e)
        } catch (e: IOError) {
            return Result.Error(e)
        } catch (e: Throwable) {
            return Result.Error(e)
        }
    }

    suspend fun getFilm(id: Int): Result<Film?> {
        try {


            // Query from cache.
            val cache = database.filmDao().getFilm(id)
            if (cache.isNotEmpty()) {
                return Result.Success(cache.first())
            }

            // Fresh request.
            val response = StarWarsService.api.getFilm(id)
            val value = response.toFilm()
            // Insert into cache.
            database.filmDao().insert(value)
            return Result.Success(value)


        } catch (e: HttpException) {
            // NOTE:
            // Special case where status code is 404 if page limit is reached.
            // Thus, return null.
            if (e.code() == 404) {
                return Result.Success(null)
            }
            return Result.Error(e)
        } catch (e: IOError) {
            return Result.Error(e)
        } catch (e: Throwable) {
            return Result.Error(e)
        }
    }
}

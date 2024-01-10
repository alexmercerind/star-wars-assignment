package com.alexmercerind.starwars.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexmercerind.starwars.model.Film

@Dao
interface FilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: Film)

    @Query("SELECT * FROM Film WHERE id = :id")
    suspend fun getFilm(id: Int): List<Film>
}

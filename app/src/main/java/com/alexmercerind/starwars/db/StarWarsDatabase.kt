package com.alexmercerind.starwars.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexmercerind.starwars.db.converters.ListConverter
import com.alexmercerind.starwars.db.dao.CharacterDao
import com.alexmercerind.starwars.db.dao.FilmDao
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.model.Film

@Database(entities = [Character::class, Film::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class StarWarsDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun filmDao(): FilmDao
}

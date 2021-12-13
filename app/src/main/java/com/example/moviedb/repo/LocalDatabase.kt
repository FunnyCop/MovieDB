package com.example.moviedb.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviedb.entity.GenreEntity
import com.example.moviedb.entity.MovieEntity
import com.example.moviedb.service.GenreDao
import com.example.moviedb.service.MovieDao
import com.example.moviedb.util.Converters

@Database(entities = [MovieEntity::class, GenreEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalDatabase: RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

}
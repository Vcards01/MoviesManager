package br.edu.ifsp.scl.moviesmanager.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.edu.ifsp.scl.moviesmanager.model.dao.MovieDAO
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Database(entities = [Movie::class], version = 1)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDAO(): MovieDAO
    companion object {
        const val MOVIE_DATABASE = "MovieManagerDatabase"
    }
}

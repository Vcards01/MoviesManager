package br.edu.ifsp.scl.moviesmanager.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie

@Dao
interface MovieDAO {
    companion object {
        const val TASK_TABLE = "movie"
    }
    @Insert
    fun insert(task: Movie)
    @Query("SELECT * FROM $TASK_TABLE")
    fun getAllMovies(): List<Movie>
    @Update
    fun update(task: Movie)
    @Delete
    fun delete(task: Movie)
}

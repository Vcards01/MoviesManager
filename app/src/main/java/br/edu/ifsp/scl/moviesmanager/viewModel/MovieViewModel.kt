package br.edu.ifsp.scl.sdm.todolist.controller

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel (application: Application): ViewModel() {
    private val movieDaoImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieDatabase::class.java,
        MovieDatabase.MOVIE_DATABASE
    ).build().movieDAO()

    val moviesMld = MutableLiveData<List<Movie>>()

    fun insertTask(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.insert(movie)
        }
    }

    fun getTasks()
    {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDaoImpl.getAllMovies()
            moviesMld.postValue(movies)
        }
    }

    fun editTask(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.update(movie)
        }
    }

    fun removeTask(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.delete(movie)
        }
    }


    companion object{
        val MovieViewModelFactory = object: ViewModelProvider.Factory{
            override fun <T: ViewModel> create(modelClass: Class<T>, extras: CreationExtras):T {
                MovieViewModel(checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])) as T
                return super.create(modelClass, extras)
            }
        }
    }
}
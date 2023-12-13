package br.edu.ifsp.scl.moviesmanager.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.room.Room
import br.edu.ifsp.scl.moviesmanager.model.database.MovieDatabase
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel (application: Application): AndroidViewModel(application) {
    private val movieDaoImpl = Room.databaseBuilder(
        application.applicationContext,
        MovieDatabase::class.java,
        MovieDatabase.MOVIE_DATABASE
    ).build().movieDAO()

    val moviesMld = MutableLiveData<List<Movie>>()

    fun insert(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.insert(movie)
        }
    }

    fun getAllMovies()
    {
        CoroutineScope(Dispatchers.IO).launch {
            val movies = movieDaoImpl.getAllMovies()
            moviesMld.postValue(movies)
        }
    }

    fun update(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.update(movie)
        }
    }

    fun delete(movie: Movie)
    {
        CoroutineScope(Dispatchers.IO).launch {
            movieDaoImpl.delete(movie)
        }
    }


    companion object {
        val MovieViewModelFactory = object : ViewModelProvider.Factory {


            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY])

                return  MovieViewModel(application) as T
            }
        }
    }
}
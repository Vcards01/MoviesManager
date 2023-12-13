package br.edu.ifsp.scl.moviesmanager.view


import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.view.adapter.MovieAdapter
import br.edu.ifsp.scl.moviesmanager.view.adapter.OnMovieClickListener
import br.edu.ifsp.scl.moviesmanager.viewModel.MovieViewModel

class ListMovieFragment : Fragment(), OnMovieClickListener{

    private lateinit var flb: FragmentListMovieBinding

    // Data source
    private val movieList: MutableList<Movie> = mutableListOf()

    // Adapter
    private val movieAdapter: MovieAdapter by lazy {
        MovieAdapter(movieList, this)
    }

    private val navController: NavController by lazy {
        findNavController()
    }

    // Communication constants
    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
        const val MOVIE_FRAGMENT_REQUEST_KEY = "MOVIE_FRAGMENT_REQUEST_KEY"
    }

    private val movieViewModel: MovieViewModel by viewModels {
        MovieViewModel.MovieViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(MOVIE_FRAGMENT_REQUEST_KEY) { requestKey, bundle ->
            if (requestKey == MOVIE_FRAGMENT_REQUEST_KEY) {
                val movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    bundle.getParcelable(EXTRA_MOVIE, Movie::class.java)
                } else {
                    bundle.getParcelable(EXTRA_MOVIE)
                }
                movie?.also { recivedMovie ->
                    movieList.indexOfFirst { it.title == recivedMovie.title }.also { position ->
                        if (position != -1) {
                            movieViewModel.update(recivedMovie)
                            movieList[position] = recivedMovie
                            movieAdapter.notifyItemChanged(position)
                        } else {
                            movieViewModel.insert(recivedMovie)
                            movieList.add(recivedMovie)
                            movieAdapter.notifyItemInserted(movieList.lastIndex)
                        }
                    }

                }
                (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                    flb.root.windowToken,
                    HIDE_NOT_ALWAYS
                )
            }

        }
        movieViewModel.moviesMld.observe(requireActivity()) { movies ->
            movieList.clear()
            movies.forEachIndexed { index, movie ->
                movieList.add(movie)
                movieAdapter.notifyItemChanged(index)
            }
        }
        movieViewModel.getAllMovies()
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            flb = FragmentListMovieBinding.inflate(inflater, container, false).apply {
                listRv.layoutManager = LinearLayoutManager(context)
                listRv.adapter = movieAdapter
                btnAdd.setOnClickListener {
                    navController.navigate(ListMovieFragmentDirections.actionListMovieFragmentToViewMovieFragment(null, editMovie=false, readMovie = false))
                }
            }

            return flb.root

        }

    override fun onMovieClick(position: Int) {
        navigateToMovieFragment(position, false, true)
    }

    override fun onRemoveMovieMenuItemClick(position: Int) {
        movieViewModel.delete(movieList[position])
        movieList.removeAt(position)
        movieAdapter.notifyItemRemoved(position)
    }

    override fun onEditMovieMenuItemClick(position: Int) {
        navigateToMovieFragment(position, true, false)
    }
    private fun navigateToMovieFragment(position: Int, editMovie: Boolean, readMovie: Boolean) {
        movieList[position].also {
            navController.navigate(
                ListMovieFragmentDirections.actionListMovieFragmentToViewMovieFragment(it, editMovie, readMovie)
            )
        }
    }


}

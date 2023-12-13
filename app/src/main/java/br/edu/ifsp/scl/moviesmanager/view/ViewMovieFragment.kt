package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentViewMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.INT_BOOL_FALSE
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.INT_BOOL_TRUE
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.INT_INVALID_SCORE
import br.edu.ifsp.scl.moviesmanager.view.ListMovieFragment.Companion.EXTRA_MOVIE
import br.edu.ifsp.scl.moviesmanager.view.ListMovieFragment.Companion.MOVIE_FRAGMENT_REQUEST_KEY

class ViewMovieFragment : Fragment() {

    private lateinit var fvm: FragmentViewMovieBinding
    private val navigationArgs: ViewMovieFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fvm= FragmentViewMovieBinding.inflate(inflater, container, false)

        val receivedMovie = navigationArgs.movie
        receivedMovie?.also { movie ->
            with(fvm) {
                commonLayout.titleEt.setText(movie.title)
                commonLayout.watchedCb.isChecked = movie.watched == INT_BOOL_TRUE
                commonLayout.scoreEt.setText(movie.stars.toString())
                commonLayout.timeEt.setText(movie.duration.toString())
                commonLayout.genreSp.setSelection(getGenrePosition(movie.genre))
                commonLayout.releaseEt.setText(movie.releaseYears)
                commonLayout.productionEt.setText(movie.production)
                commonLayout.urlImgEt.setText(movie.url)
                navigationArgs.editMovie.also { editMovie ->
                    commonLayout.titleEt.isEnabled = editMovie
                    commonLayout.watchedCb.isEnabled = editMovie
                    commonLayout.saveBt.visibility = if (editMovie) VISIBLE else GONE
                }

            }
        }

        fvm.run {
            commonLayout.saveBt.setOnClickListener{
                setFragmentResult(MOVIE_FRAGMENT_REQUEST_KEY, Bundle().apply {
                    if (validate()) {
                        val name = commonLayout.titleEt.text.toString()
                        val releaseYears = commonLayout.releaseEt.text.toString()
                        val production = commonLayout.productionEt.text.toString()
                        val minutes = commonLayout.timeEt.text.toString().toLong()
                        val watched =
                            if (commonLayout.watchedCb.isChecked) INT_BOOL_TRUE else INT_BOOL_FALSE
                        val stars =
                            if (commonLayout.scoreEt.text.toString() != "") commonLayout.scoreEt.text.toString()
                                .toInt() else INT_INVALID_SCORE
                        val genre = (commonLayout.genreSp.selectedView as TextView).text.toString()
                        val url = commonLayout.urlImgEt.text.toString()
                        putParcelable(
                            EXTRA_MOVIE, Movie(name, releaseYears, production, minutes, watched, stars, genre, url)
                        )
                    }
                })
                findNavController().navigateUp()
            }
        }

        return fvm.root


    }
    private fun getGenrePosition(genre: String): Int {
        val genresArray = resources.getStringArray(R.array.genres)
        return genresArray.indexOf(genre)
    }

    private fun validate(): Boolean {
        if (
            fvm.commonLayout.titleEt.text.isEmpty() ||
            fvm.commonLayout.releaseEt.text.isEmpty() ||
            fvm.commonLayout.timeEt.text.isEmpty() ||
            fvm.commonLayout.productionEt.text.isEmpty() ||
            fvm.commonLayout.urlImgEt.text.isEmpty() ||
            (fvm.commonLayout.scoreEt.text.isNotEmpty() && fvm.commonLayout.scoreEt.text.toString().toInt() > 10)
        )
            return false
        return true
    }


}
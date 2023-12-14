package br.edu.ifsp.scl.moviesmanager.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.TileMovieBinding
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie
import br.edu.ifsp.scl.moviesmanager.model.entity.Movie.Companion.INT_BOOL_TRUE
import com.bumptech.glide.Glide

class MovieAdapter(
    private val movieList: List<Movie>,
    private val onMovieClickListener: OnMovieClickListener
) : RecyclerView.Adapter<MovieAdapter.MovieTileViewHolder>() {
    inner class MovieTileViewHolder(tileMovieBinding: TileMovieBinding) :
        RecyclerView.ViewHolder(tileMovieBinding.root) {
        val titleTv: TextView = tileMovieBinding.titleTv
        val urlTv: ImageView = tileMovieBinding.imgUrlIm
        val genreTv: TextView = tileMovieBinding.genreTv
        val watchedTv: TextView = tileMovieBinding.watchedTv
        val scoreTv: TextView = tileMovieBinding.scoreTv

        init {
            tileMovieBinding.apply {
                root.run {
                    setOnCreateContextMenuListener { menu, _, _ ->
                        (onMovieClickListener as? Fragment)?.activity?.menuInflater?.inflate(
                            R.menu.context_menu_movie,
                            menu
                        )
                        menu?.findItem(R.id.removeMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onRemoveMovieMenuItemClick(adapterPosition)
                            true
                        }
                        menu?.findItem(R.id.editMovieMi)?.setOnMenuItemClickListener {
                            onMovieClickListener.onEditMovieMenuItemClick(adapterPosition)
                            true
                        }
                    }
                    setOnClickListener {
                        onMovieClickListener.onMovieClick(adapterPosition)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = TileMovieBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).run { MovieTileViewHolder(this) }


    override fun onBindViewHolder(holder: MovieTileViewHolder, position: Int) {
        movieList[position].let { movie ->
            with(holder) {
                titleTv.text = movie.title
                val context = urlTv.context
                var url = "https://blog.springshare.com/wp-content/uploads/2010/02/nc-md.gif"
                if(movieList[position].url.isNotEmpty()){
                    url = movieList[position].url
                }
                Glide.with(context).load(url).into(urlTv)
                genreTv.text = movie.genre
                watchedTv.text =  if (movie.watched == INT_BOOL_TRUE) "Assistido" else "Não assistido"
                if (movie.score >=0){
                    scoreTv.text = movie.score.toString() + " ⭐"
                }else{
                    scoreTv.text = ""
                }


            }
        }
    }

    override fun getItemCount() = movieList.size
}
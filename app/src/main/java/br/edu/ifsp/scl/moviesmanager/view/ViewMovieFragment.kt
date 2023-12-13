package br.edu.ifsp.scl.moviesmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.edu.ifsp.scl.moviesmanager.viewModel.MovieViewModel
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentViewMovieBinding

class ViewMovieFragment : Fragment() {

    private lateinit var vmfb: FragmentViewMovieBinding
    companion object {
        fun newInstance() = ViewMovieFragment()
    }

    private lateinit var viewModel: MovieViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vmfb= FragmentViewMovieBinding.inflate(inflater, container, false)

        return vmfb.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
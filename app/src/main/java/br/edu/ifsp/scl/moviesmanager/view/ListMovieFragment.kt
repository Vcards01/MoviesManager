package br.edu.ifsp.scl.moviesmanager.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding

class ListMovieFragment : Fragment(){

    private var _binding: FragmentListMovieBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMovieBinding.inflate(inflater, container, false)

        return binding.root

    }

}



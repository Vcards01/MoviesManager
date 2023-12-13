package br.edu.ifsp.scl.moviesmanager.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import br.edu.ifsp.scl.moviesmanager.R
import br.edu.ifsp.scl.moviesmanager.databinding.FragmentListMovieBinding

class ListMovieFragment : Fragment(){

    private var _binding: FragmentListMovieBinding? = null

    private val binding get() = _binding!!

    private val navController: NavController by lazy {
        findNavController()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListMovieBinding.inflate(inflater, container, false)

        _binding?.apply {
            btnAdd.setOnClickListener{
                navController.navigate(R.id.action_listMovieFragment_to_viewMovieFragment)
            }
        }

        return binding.root

    }

}



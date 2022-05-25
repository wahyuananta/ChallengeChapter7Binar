package com.coder.challengechapter7binar.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter5binar.R
import com.coder.challengechapter5binar.adapter.PopularMovieAdapter
import com.coder.challengechapter5binar.adapter.UpcomingMovieAdapter
import com.coder.challengechapter5binar.api.model.ResultPopularMovieResponse
import com.coder.challengechapter5binar.api.model.ResultUpcomingMovieResponse
import com.coder.challengechapter5binar.databinding.FragmentHomeBinding
import com.coder.challengechapter5binar.datastore.UserDataStoreManager
import com.coder.challengechapter5binar.room.UserEntity
import com.coder.challengechapter5binar.room.UserRepository
import com.coder.challengechapter5binar.viewmodel.HomeViewModel
import com.coder.challengechapter5binar.viewmodel.MainViewModel
import com.coder.challengechapter5binar.viewmodel.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private lateinit var repository: UserRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel
    private lateinit var pref: UserDataStoreManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = UserRepository(requireContext())

        pref = UserDataStoreManager(requireContext())
        mainViewModel = ViewModelProvider(requireActivity(), ViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getDataStore().observe(viewLifecycleOwner) {
            binding!!.tvUsername.text = it.username
        }

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding!!.btnFavorite.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }

        fetchAllUpcomingMovie()
        fetchAllPopularMovie()
        updateUser()
    }

    private fun fetchAllUpcomingMovie() {
        viewModel.getUpcomingMovie()
        viewModel.upcomingMovie.observe(viewLifecycleOwner) {
            showListUpcomingMovie(it.resultsUpcomingMovieResponse)
            binding!!.pbLoading.visibility = View.GONE
        }
    }

    private fun showListUpcomingMovie(data: List<ResultUpcomingMovieResponse>?) {
        val adapter = UpcomingMovieAdapter(object : UpcomingMovieAdapter.OnClickListener {
            override fun onClickItem(data: ResultUpcomingMovieResponse) {
                val homeToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding!!.rvListTopRated.adapter = adapter
    }

    private fun fetchAllPopularMovie() {
        viewModel.getPopularMovie()
        viewModel.popularMovie.observe(viewLifecycleOwner) {
            showListPopularMovie(it.resultPopularMovieRespons)
            binding!!.pbLoading.visibility = View.GONE
        }
    }

    private fun showListPopularMovie(data: List<ResultPopularMovieResponse>?) {
        val adapter = PopularMovieAdapter(object : PopularMovieAdapter.OnClickListener {
            override fun onClickItem(data: ResultPopularMovieResponse) {
                val homeToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding!!.rvListPopular.adapter = adapter
    }

    private fun updateUser(){
        binding!!.btnUpdate.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val dataUser = repository.getUser(binding!!.tvUsername.text.toString())
                runBlocking(Dispatchers.Main) {
                    if (dataUser != null) {
                        val user = UserEntity(
                            dataUser.id,
                            dataUser.username,
                            dataUser.email,
                            dataUser.password,
                            dataUser.uri
                        )
                        val navigateUpdate = HomeFragmentDirections.actionHomeFragmentToProfileFragment(user)
                        findNavController().navigate(navigateUpdate)
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAllPopularMovie()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
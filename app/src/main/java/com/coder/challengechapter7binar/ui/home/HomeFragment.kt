package com.coder.challengechapter7binar.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.coder.challengechapter7binar.adapter.PopularMovieAdapter
import com.coder.challengechapter7binar.adapter.UpcomingMovieAdapter
import com.coder.challengechapter7binar.data.api.Status
import com.coder.challengechapter7binar.data.api.model.ResultPopularMovieResponse
import com.coder.challengechapter7binar.data.api.model.ResultUpcomingMovieResponse
import com.coder.challengechapter7binar.data.room.entity.UserEntity
import com.coder.challengechapter7binar.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModels()

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

        homeViewModel.getDataStore()
        homeViewModel.userDataStore.observe(viewLifecycleOwner) {
            binding!!.tvUsername.text = it.username
        }

        fetchAllUpcomingMovie()
        fetchAllPopularMovie()
        updateUser()
    }

    private fun fetchAllUpcomingMovie() {
        homeViewModel.upcomingMovie.observe(viewLifecycleOwner) {
            when(it.status){
                Status.LOADING -> {
                    binding!!.pbLoading.visibility = View.VISIBLE
                    binding!!.tvUpcomingMovie.visibility = View.GONE
                    binding!!.tvPopularMovie.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding!!.pbLoading.visibility = View.GONE
                    binding!!.tvUpcomingMovie.visibility = View.VISIBLE
                    binding!!.tvPopularMovie.visibility = View.VISIBLE
                    showListUpcomingMovie(it.data?.resultsUpcomingMovieResponse)
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        homeViewModel.getUpcomingMovie()
    }

    private fun showListUpcomingMovie(data: List<ResultUpcomingMovieResponse>?) {
        val adapter = UpcomingMovieAdapter(object : UpcomingMovieAdapter.OnClickListener {
            override fun onClickItem(data: ResultUpcomingMovieResponse) {
                val homeToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding?.rvListTopRated?.adapter = adapter
    }

    private fun fetchAllPopularMovie() {
        homeViewModel.popularMovie.observe(viewLifecycleOwner) {
            when(it.status){
                Status.LOADING -> {
                    binding!!.pbLoading.visibility = View.VISIBLE
                    binding!!.tvUpcomingMovie.visibility = View.GONE
                    binding!!.tvPopularMovie.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding!!.pbLoading.visibility = View.GONE
                    binding!!.tvUpcomingMovie.visibility = View.VISIBLE
                    binding!!.tvPopularMovie.visibility = View.VISIBLE
                    showListPopularMovie(it.data?.resultPopularMovieRespons)
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
        homeViewModel.getPopularMovie()
    }

    private fun showListPopularMovie(data: List<ResultPopularMovieResponse>?) {
        val adapter = PopularMovieAdapter(object : PopularMovieAdapter.OnClickListener {
            override fun onClickItem(data: ResultPopularMovieResponse) {
                val homeToDetail = HomeFragmentDirections.actionHomeFragmentToDetailFragment(data.id)
                findNavController().navigate(homeToDetail)
            }
        })

        adapter.submitData(data)
        binding?.rvListPopular?.adapter = adapter
    }

    private fun updateUser(){
        binding?.btnUpdate?.setOnClickListener {
            homeViewModel.getUser(binding?.tvUsername?.text.toString())
            homeViewModel.getDataUser.observe(viewLifecycleOwner) {
                if (it != null) {
                    val user = UserEntity(
                        it.id,
                        it.username,
                        it.email,
                        it.password,
                        it.uri
                    )
                    val navigateUpdate = HomeFragmentDirections.actionHomeFragmentToProfileFragment(user)
                    findNavController().navigate(navigateUpdate)
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
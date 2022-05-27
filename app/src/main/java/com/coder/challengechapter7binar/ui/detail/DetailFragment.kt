package com.coder.challengechapter7binar.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.api.Status
import com.coder.challengechapter7binar.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val imageBase = "https://image.tmdb.org/t/p/w500/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idMovie = args.id
        detailViewModel.getMovieById(idMovie)
        detailViewModel.detailMovie.observe(viewLifecycleOwner) {
            when(it.status){
                Status.LOADING -> {
                    binding.pbLoading.visibility = View.VISIBLE
                    binding.contentDescription.visibility = View.GONE
                    binding.cvPoster.visibility = View.GONE
                    binding.flBackdrop.visibility = View.GONE
                    binding.ivBackdrop.visibility = View.GONE
                    binding.ivLogo.visibility = View.GONE
                    binding.ivPoster.visibility = View.GONE
                    binding.tvApp.visibility = View.GONE
                    binding.tvDescriptionTitle.visibility = View.GONE
                    binding.tvDescriptionValue.visibility = View.GONE
                    binding.tvDuration.visibility = View.GONE
                    binding.tvMinutes.visibility = View.GONE
                    binding.tvMovie.visibility = View.GONE
                    binding.tvMovieTitleValue.visibility = View.GONE
                    binding.tvReleaseDate.visibility = View.GONE
                    binding.tvTaglineTitle.visibility = View.GONE
                    binding.tvTaglineValue.visibility = View.GONE
                    binding.tvTitleDuration.visibility = View.GONE
                    binding.tvTitleRelease.visibility = View.GONE
                    binding.tvVoteAverage.visibility = View.GONE
                    binding.linearTv.visibility = View.GONE
                }
                Status.SUCCESS -> {
                    binding.pbLoading.visibility = View.GONE
                    binding.contentDescription.visibility = View.VISIBLE
                    binding.cvPoster.visibility = View.VISIBLE
                    binding.flBackdrop.visibility = View.VISIBLE
                    binding.ivBackdrop.visibility = View.VISIBLE
                    binding.ivLogo.visibility = View.VISIBLE
                    binding.ivPoster.visibility = View.VISIBLE
                    binding.tvApp.visibility = View.VISIBLE
                    binding.tvDescriptionTitle.visibility = View.VISIBLE
                    binding.tvDescriptionValue.visibility = View.VISIBLE
                    binding.tvDuration.visibility = View.VISIBLE
                    binding.tvMinutes.visibility = View.VISIBLE
                    binding.tvMovie.visibility = View.VISIBLE
                    binding.tvMovieTitleValue.visibility = View.VISIBLE
                    binding.tvReleaseDate.visibility = View.VISIBLE
                    binding.tvTaglineTitle.visibility = View.VISIBLE
                    binding.tvTaglineValue.visibility = View.VISIBLE
                    binding.tvTitleDuration.visibility = View.VISIBLE
                    binding.tvTitleRelease.visibility = View.VISIBLE
                    binding.tvVoteAverage.visibility = View.VISIBLE
                    binding.linearTv.visibility = View.VISIBLE

                    //  Set Image Backdrop
                    val imageBackDrop = it.data?.backdropPath
                    if (imageBackDrop == null) {
                        Glide.with(binding.root
                        ).load(R.drawable.ic_launcher_background)
                            .into(binding.ivBackdrop)
                    } else {
                        Glide.with(binding.root
                        ).load(imageBase + imageBackDrop)
                            .into(binding.ivBackdrop)
                    }

                    //  Set Image Poster
                    val imagePoster = it.data?.posterPath
                    if (imagePoster == null) {
                        Glide.with(binding.root
                        ).load(R.drawable.ic_launcher_background)
                            .into(binding.ivPoster)
                    } else {
                        Glide.with(binding.root
                        ).load(imageBase + imagePoster)
                            .into(binding.ivPoster)
                    }

                    //  Set Title Movie and Elipsize Marquee (Untuk text berjalan)
                    binding.tvMovieTitleValue.text = it.data?.title
                    binding.tvMovieTitleValue.isSelected = true

                    //  Set Description Movie
                    binding.tvDescriptionValue.text = it.data?.overview

                    //  Set vote, duration and release date movie
                    binding.tvVoteAverage.text = it.data?.voteAverage.toString()
                    binding.tvDuration.text = it.data?.runtime.toString()
                    binding.tvReleaseDate.text = it.data?.releaseDate

                    //  Set Tagline Movie
                    val tagline = it.data?.tagline
                    if (tagline == "") {
                        binding.tvTaglineTitle.visibility = View.GONE
                        binding.tvTaglineValue.visibility = View.GONE
                    } else {
                        binding.tvTaglineValue.text = it.data?.tagline
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
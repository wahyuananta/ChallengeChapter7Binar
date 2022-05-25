package com.coder.challengechapter7binar.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val detailViewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
//    private lateinit var viewModel: DetailViewModel

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
//        viewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getMovieById(idMovie)
        detailViewModel.detailMovie.observe(viewLifecycleOwner) {
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

        binding.btnBack.setOnClickListener {
            findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
        }

        binding.ivFavorite.setOnClickListener {
            binding.ivFavorite.setImageResource(R.drawable.ic_favorite_black_24dp)
            binding.ivFavorite.setOnClickListener {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_border_black_24dp)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
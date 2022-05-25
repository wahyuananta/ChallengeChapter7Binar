package com.coder.challengechapter7binar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.api.model.ResultPopularMovieResponse
import com.coder.challengechapter7binar.databinding.ItemContentBinding

class PopularMovieAdapter(private val onItemClick: OnClickListener) :
    RecyclerView.Adapter<PopularMovieAdapter.ViewHolder>() {

    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

    private val diffCallback = object : DiffUtil.ItemCallback<ResultPopularMovieResponse>() {
        override fun areItemsTheSame(
            oldItem: ResultPopularMovieResponse,
            newItem: ResultPopularMovieResponse
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResultPopularMovieResponse,
            newItem: ResultPopularMovieResponse
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<ResultPopularMovieResponse>?) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemContentBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = differ.currentList[position]
        data.let { holder.bind(data) }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder(private val binding: ItemContentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultPopularMovieResponse) {
            binding.apply {
                val image = data.posterPath
                if (image == null) {
                    Glide.with(binding.root
                    ).load(R.drawable.ic_launcher_background)
                        .into(ivMovie)
                } else {
                    Glide.with(binding.root
                    ).load(IMAGE_BASE + image)
                        .into(ivMovie)
                }
                tvMovieTitle.text = data.title
                tvMovieTitle.isSelected = true
                tvVoteAverage.text = data.voteAverage.toString()
                root.setOnClickListener {
                    onItemClick.onClickItem(data)
                }
            }
        }
    }

    interface OnClickListener {
        fun onClickItem(data: ResultPopularMovieResponse)
    }
}
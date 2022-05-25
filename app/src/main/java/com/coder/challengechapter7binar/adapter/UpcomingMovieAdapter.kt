package com.coder.challengechapter7binar.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coder.challengechapter7binar.R
import com.coder.challengechapter7binar.data.api.model.ResultUpcomingMovieResponse
import com.coder.challengechapter7binar.databinding.ItemContentBinding

class UpcomingMovieAdapter(private val onItemClick: OnClickListener) :
    RecyclerView.Adapter<UpcomingMovieAdapter.ViewHolder>() {

    private val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"

    private val diffCallback = object : DiffUtil.ItemCallback<ResultUpcomingMovieResponse>() {
        override fun areItemsTheSame(
            oldItem: ResultUpcomingMovieResponse,
            newItem: ResultUpcomingMovieResponse
        ): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ResultUpcomingMovieResponse,
            newItem: ResultUpcomingMovieResponse
        ): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitData(value: List<ResultUpcomingMovieResponse>?) = differ.submitList(value)

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
        fun bind(data: ResultUpcomingMovieResponse) {
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
        fun onClickItem(data: ResultUpcomingMovieResponse)
    }
}
package com.alexmercerind.starwars.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexmercerind.starwars.databinding.LayoutFilmBinding
import com.alexmercerind.starwars.model.Film

class FilmAdapter : PagingDataAdapter<Film, FilmAdapter.ViewHolder>(FilmDiffCallback) {
    inner class ViewHolder(val binding: LayoutFilmBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            title.text = getItem(position)?.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutFilmBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )
}

private object FilmDiffCallback : DiffUtil.ItemCallback<Film>() {
    override fun areItemsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Film, newItem: Film): Boolean = oldItem == newItem

}

package com.alexmercerind.starwars.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexmercerind.starwars.R
import com.alexmercerind.starwars.api.StarWarsService
import com.alexmercerind.starwars.databinding.LayoutCharacterBinding
import com.alexmercerind.starwars.model.Character
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CharacterAdapter :
    PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(CharacterDiffCallback) {
    inner class ViewHolder(val binding: LayoutCharacterBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.root.setOnClickListener {
            GlobalScope.launch {
                StarWarsService.api.getFilm(1)
            }
        }
        holder.binding.apply {
            nameTextView.text = getItem(position)?.name
            heightTextView.text = root.context.getString(R.string.character_height, getItem(position)?.height)
            massTextView.text = root.context.getString(R.string.character_mass, getItem(position)?.mass)
            genderTextView.text = root.context.getString(R.string.character_gender, getItem(position)?.gender)
            createdTextView.text = root.context.getString(R.string.character_created, getItem(position)?.created)
            updatedTextView.text = root.context.getString(R.string.character_updated, getItem(position)?.edited)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}

private object CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem == newItem

}

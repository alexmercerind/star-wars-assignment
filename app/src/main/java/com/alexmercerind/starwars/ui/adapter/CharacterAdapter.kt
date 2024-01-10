package com.alexmercerind.starwars.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alexmercerind.starwars.R
import com.alexmercerind.starwars.databinding.LayoutCharacterBinding
import com.alexmercerind.starwars.model.Character
import com.alexmercerind.starwars.ui.CharactersListFragmentDirections
import com.alexmercerind.starwars.ui.MainActivityViewModel
import com.alexmercerind.starwars.utils.Constants

class CharacterAdapter(private val mainActivityViewModel: MainActivityViewModel) :
    PagingDataAdapter<Character, CharacterAdapter.ViewHolder>(CharacterDiffCallback) {
    inner class ViewHolder(val binding: LayoutCharacterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.materialCardView.setOnClickListener {
            Log.d(Constants.LOG_TAG, "!!!")

            mainActivityViewModel.navController?.navigate(
                CharactersListFragmentDirections.actionCharactersListFragmentToFilmsListFragment(
                    getItem(position)!!.name,
                    getItem(position)!!.films.toTypedArray(),
                )
            )
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

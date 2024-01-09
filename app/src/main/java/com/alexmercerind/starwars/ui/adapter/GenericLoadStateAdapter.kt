package com.alexmercerind.starwars.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexmercerind.starwars.databinding.LayoutLoadStateBinding

class GenericLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<GenericLoadStateAdapter.ViewHolder>() {
    inner class ViewHolder(
        private val binding: LayoutLoadStateBinding, private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.retryMaterialButton.setOnClickListener { retry() }
            when (loadState) {
                is LoadState.Loading -> {
                    binding.circularProgressIndicator.visibility = View.VISIBLE
                    binding.retryMaterialButton.visibility = View.GONE
                }

                is LoadState.Error -> {
                    binding.circularProgressIndicator.visibility = View.GONE
                    binding.retryMaterialButton.visibility = View.VISIBLE
                }

                is LoadState.NotLoading -> {
                    binding.circularProgressIndicator.visibility = View.GONE
                    binding.retryMaterialButton.visibility = View.GONE
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        LayoutLoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false), retry
    )

}
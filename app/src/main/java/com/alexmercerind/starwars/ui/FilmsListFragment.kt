package com.alexmercerind.starwars.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.alexmercerind.starwars.R
import com.alexmercerind.starwars.databinding.FragmentFilmsListBinding
import com.alexmercerind.starwars.ui.adapter.FilmAdapter
import com.alexmercerind.starwars.ui.adapter.GenericLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FilmsListFragment : Fragment() {
    private val args: FilmsListFragmentArgs by navArgs()

    private val filmsListViewModel: FilmsListViewModel by viewModels()

    private var _binding: FragmentFilmsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmsListBinding.inflate(inflater, container, false)

        val items = filmsListViewModel.getPagingDataForFilms(args.filmURLs.toList())
        val filmAdapter = FilmAdapter()

        val genericLoadStateAdapter = GenericLoadStateAdapter(filmAdapter::retry)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    filmAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                filmAdapter.loadStateFlow.collectLatest {
                    if (filmAdapter.itemCount > 0) {
                        binding.retryMaterialButton.visibility = View.GONE
                        binding.circularProgressIndicator.visibility = View.GONE
                    }
                    if (filmAdapter.itemCount == 0 && it.refresh is LoadState.Error) {
                        binding.retryMaterialButton.visibility = View.VISIBLE
                        binding.circularProgressIndicator.visibility = View.GONE
                    }
                    if (filmAdapter.itemCount == 0 && it.refresh is LoadState.Loading) {
                        binding.retryMaterialButton.visibility = View.GONE
                        binding.circularProgressIndicator.visibility = View.VISIBLE
                    }
                }
            }
        }

        binding.titleMaterialToolbar.title =
            requireContext().getString(R.string.character_films, args.character)

        binding.filmsRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == filmAdapter.itemCount && genericLoadStateAdapter.itemCount > 0) {
                            2
                        } else {
                            1
                        }
                    }
                }
            }
        binding.filmsRecyclerView.adapter = filmAdapter.withLoadStateFooter(
            footer = genericLoadStateAdapter
        )

        binding.retryMaterialButton.setOnClickListener {
            filmAdapter.refresh()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

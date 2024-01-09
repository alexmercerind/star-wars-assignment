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
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.alexmercerind.starwars.databinding.FragmentCharactersListBinding
import com.alexmercerind.starwars.ui.adapter.CharacterAdapter
import com.alexmercerind.starwars.ui.adapter.GenericLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersListFragment : Fragment() {
    private val charactersListViewModel: CharactersListViewModel by viewModels()

    private var _binding: FragmentCharactersListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCharactersListBinding.inflate(inflater, container, false)

        val items = charactersListViewModel.items
        val characterAdapter = CharacterAdapter()
        val genericLoadStateAdapter = GenericLoadStateAdapter(characterAdapter::retry)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                items.collectLatest {
                    characterAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                characterAdapter.loadStateFlow.collectLatest {
                    if (characterAdapter.itemCount > 0) {
                        binding.circularProgressIndicator.visibility = View.GONE
                    }
                }
            }
        }

        binding.charactersRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), 2).apply {
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (position == characterAdapter.itemCount && genericLoadStateAdapter.itemCount > 0) {
                            2
                        } else {
                            1
                        }
                    }
                }
            }
        binding.charactersRecyclerView.adapter = characterAdapter.withLoadStateFooter(
            footer = genericLoadStateAdapter
        )

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
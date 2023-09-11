package com.gmailclone.ui.main.fragments

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.gmailclone.R
import com.gmailclone.databinding.FragmentSearchSortBinding
import com.gmailclone.fake.FakeEmailGenerator
import com.gmailclone.ui.main.fragments.adapters.FilterItemsAdapter
import com.gmailclone.ui.main.fragments.adapters.SearchItemAdapter
import com.gmailclone.ui.main.viewmodel.SharedViewModel

class SearchSortFragment : Fragment(R.layout.fragment_search_sort) {

    private lateinit var binding: FragmentSearchSortBinding
    private val filterItemsAdapter = FilterItemsAdapter()
    private var searchAdapter = SearchItemAdapter()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchSortBinding.bind(view)

        searchAdapter.updateData(FakeEmailGenerator.fakeEmailList)

        filterItemsAdapter.setFilters(
            listOf(
                FilterOption("Labels"),
                FilterOption("From"),
                FilterOption("To"),
                FilterOption("Attachment"),
                FilterOption("Date"),
                FilterOption("Is unread"),
                FilterOption("Exclude calendar updates"),
            )
        )

        sharedViewModel.enteredText.observe(viewLifecycleOwner) {
            binding.tvSearchText.apply {
                visibility = if (it.isNotEmpty()) {
                    text = "Search for \"$it\" in mail"
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

            searchAdapter.filter.filter(it)
        }
        setupView()
    }

    private fun setupView() {
        binding.recyclerViewEmail.adapter = searchAdapter
        searchAdapter.setupRecyclerView(binding.recyclerViewEmail)
        searchAdapter.setupContext(requireContext())

        binding.filterItems.adapter = filterItemsAdapter
        filterItemsAdapter.setContext(requireContext())
        filterItemsAdapter.setItemColor(false)
    }
}
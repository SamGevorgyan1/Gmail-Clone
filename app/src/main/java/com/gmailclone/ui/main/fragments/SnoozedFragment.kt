package com.gmailclone.ui.main.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gmailclone.R
import com.gmailclone.databinding.FragmentSnoozedBinding
import com.gmailclone.ui.main.fragments.adapters.FilterItemsAdapter

class SnoozedFragment : Fragment(R.layout.fragment_snoozed) {

    private val filterAdapter = FilterItemsAdapter()
    private lateinit var binding: FragmentSnoozedBinding

    private val filterOptions = listOf(
        FilterOption("Snoozed"),
        FilterOption("From"),
        FilterOption("To"),
        FilterOption("Attachment"),
        FilterOption("Date"),
        FilterOption("Is unread"),
        FilterOption("Exclude calendar updates"),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding =FragmentSnoozedBinding.bind(view)
        setupViews()
    }

    private fun setupViews() {
        binding.filterItems.adapter = filterAdapter
        filterAdapter.setFilters(filterOptions)
        filterAdapter.setContext(requireContext())
    }
}
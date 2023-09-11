package com.gmailclone.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmailclone.R
import com.gmailclone.databinding.FragmentStarredBinding
import com.gmailclone.ui.main.fragments.adapters.EmailContentAdapter
import com.gmailclone.ui.main.fragments.adapters.FilterItemsAdapter
import com.gmailclone.ui.main.viewmodel.EmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StarredFragment : Fragment(R.layout.fragment_starred) {

    private val filterItemsAdapter = FilterItemsAdapter()
    private val viewModel by viewModel<EmailViewModel>()
    private val emailContentAdapter = EmailContentAdapter()

    private val filters = listOf(
        FilterOption("Starred"),
        FilterOption("From"),
        FilterOption("To"),
        FilterOption("Attachment"),
        FilterOption("Date"),
        FilterOption("Is unread"),
        FilterOption("Exclude calendar updates"),
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getAllEmails()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentStarredBinding.bind(view)
        setupView(binding)

        viewModel.emailList.observe(viewLifecycleOwner) { emailContentList ->
            emailContentAdapter.updateData(emailContentList.filter { it.starred })
            binding.ivStar.visibility = if (emailContentList.any { it.starred }) View.GONE else View.VISIBLE
        }
    }

    private fun setupView(binding: FragmentStarredBinding) {
        binding.filterItems.adapter = filterItemsAdapter
        binding.recyclerViewEmail.adapter = emailContentAdapter
        filterItemsAdapter.setFilters(filters)
        filterItemsAdapter.setContext(requireContext())
    }
}

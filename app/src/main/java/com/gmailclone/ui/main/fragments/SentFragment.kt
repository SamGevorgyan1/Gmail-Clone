package com.gmailclone.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmailclone.R
import com.gmailclone.databinding.FragmentSentBinding
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.ui.main.fragments.adapters.EmailContentAdapter
import com.gmailclone.ui.main.fragments.adapters.FilterItemsAdapter
import com.gmailclone.ui.main.viewmodel.EmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SentFragment : Fragment(R.layout.fragment_sent) {

    private lateinit var binding: FragmentSentBinding
    private val filterItemsAdapter = FilterItemsAdapter()
    private var emailDetailsCallBack: EmailDetailsListener? = null
    private val emailContentAdapter = EmailContentAdapter { data ->
        emailDetailsCallBack?.openEmailDetail(
            R.id.action_nav_sent_to_nav_email_detail,
            data
        )
    }
    private val viewModel by viewModel<EmailViewModel>()

    private val filters = listOf(
        FilterOption("Sent"),
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
        return inflater.inflate(R.layout.fragment_sent, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSentBinding.bind(view)

        setupViews()

        viewModel.emailList.observe(viewLifecycleOwner) { emailContentList ->
            emailContentAdapter.updateData(emailContentList.filter { it.isSent })
        }

        emailContentAdapter.setupContext(requireContext())
        filterItemsAdapter.setContext(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EmailDetailsListener) {
            emailDetailsCallBack = context
        }
    }

    private fun setupViews() {
        binding.filterItems.adapter = filterItemsAdapter
        binding.recyclerViewEmails.adapter = emailContentAdapter
        filterItemsAdapter.setFilters(filters)
    }
}

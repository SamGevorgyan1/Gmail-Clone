package com.gmailclone.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.gmailclone.R
import com.gmailclone.databinding.FragmentAllMailBinding
import com.gmailclone.fake.FakeEmailGenerator
import com.gmailclone.model.EmailContent
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.ui.main.callbacks.ScrollListener
import com.gmailclone.ui.main.fragments.adapters.EmailContentAdapter
import com.gmailclone.ui.main.fragments.adapters.FilterItemsAdapter
import com.gmailclone.ui.main.viewmodel.EmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMailFragment : Fragment(R.layout.fragment_all_mail) {

    private lateinit var binding: FragmentAllMailBinding
    private val filterItemsAdapter = FilterItemsAdapter()
    private val allEmailList: MutableList<EmailContent> = mutableListOf()
    private val emailContentAdapter = EmailContentAdapter { itemListener(it) }
    private var scrollListener: ScrollListener? = null
    private var emailDetailsListener: EmailDetailsListener? = null
    private val viewModel by viewModel<EmailViewModel>()

    private val filters = listOf(
        FilterOption("All mail"),
        FilterOption("From"),
        FilterOption("To"),
        FilterOption("Attachment"),
        FilterOption("Date"),
        FilterOption("Is unread"),
        FilterOption("Exclude calendar updates")
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
        binding = FragmentAllMailBinding.bind(view)
        setupViews()
        emailContentAdapter.setupContext(requireContext())

        viewModel.emailList.observe(viewLifecycleOwner) {
            allEmailList.addAll(it)
            emailContentAdapter.updateData(allEmailList)
        }
        allEmailList.addAll(FakeEmailGenerator.fakeEmailList)
        emailContentAdapter.updateData(allEmailList)

        binding.recyclerViewEmails.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 2) scrollListener?.onScrollDown()
                else scrollListener?.onScrollUp()
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        scrollListener = context as? ScrollListener ?: throw IllegalStateException("Activity must implement ScrollListener")
        emailDetailsListener = context as? EmailDetailsListener
    }

    private fun setupViews() {
        binding.recyclerViewEmails.adapter = emailContentAdapter
        binding.filterItems.adapter = filterItemsAdapter
        filterItemsAdapter.setFilters(filters)
        filterItemsAdapter.setContext(requireContext())
    }

    private fun itemListener(emailContent: EmailContent) {
        emailDetailsListener?.openEmailDetail(
            R.id.action_nav_all_mail_to_nav_important,
            emailContent
        )
    }
}

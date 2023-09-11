package com.gmailclone.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gmailclone.R
import com.gmailclone.databinding.FragmentPrimaryBinding
import com.gmailclone.fake.FakeEmailGenerator
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.ui.main.fragments.adapters.EmailContentAdapter

class PrimaryFragment : Fragment(R.layout.fragment_primary) {

    private lateinit var binding: FragmentPrimaryBinding
    private val emailContentAdapter = EmailContentAdapter { emailContent ->
        emailDetailsListener?.openEmailDetail(
            R.id.action_nav_primary_to_nav_important,
            emailContent
        )
    }
    private var emailDetailsListener: EmailDetailsListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrimaryBinding.bind(view)

        setupViews()

        emailContentAdapter.updateData(FakeEmailGenerator.fakeEmailList)
        emailContentAdapter.setupContext(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EmailDetailsListener) {
            emailDetailsListener = context
        }
    }

    private fun setupViews() {
        binding.recyclerViewEmails.adapter = emailContentAdapter
    }
}

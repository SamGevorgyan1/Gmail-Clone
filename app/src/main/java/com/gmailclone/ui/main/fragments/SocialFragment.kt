package com.gmailclone.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.gmailclone.R
import com.gmailclone.databinding.FragmentSocialBinding
import com.gmailclone.fake.FakeEmailGenerator
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.ui.main.fragments.adapters.EmailContentAdapter

class SocialFragment : Fragment(R.layout.fragment_social) {

    private lateinit var binding: FragmentSocialBinding
    private val emailContentAdapter = EmailContentAdapter { emailContent ->
        emailDetailsListener?.openEmailDetail(
            R.id.action_nav_social_to_nav_email_detail,
            emailContent
        )
    }
    private var emailDetailsListener: EmailDetailsListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSocialBinding.bind(view)

        binding.tvPageName.text = "Social"
        binding.recyclerViewEmail.adapter = emailContentAdapter

        emailContentAdapter.updateData(FakeEmailGenerator.fakeEmailList)
        emailContentAdapter.setupContext(requireContext())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EmailDetailsListener) {
            emailDetailsListener = context
        }
    }
}

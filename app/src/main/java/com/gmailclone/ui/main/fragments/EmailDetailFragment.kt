package com.gmailclone.ui.main.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.gmailclone.R
import com.gmailclone.databinding.FragmentEmailDetailBinding
import com.gmailclone.model.EmailContent
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.utils.Constants.KEY_EMAIL_DETAIL_OBJECT
import com.gmailclone.ui.main.viewmodel.EmailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EmailDetailFragment : Fragment(R.layout.fragment_email_detail) {

    private lateinit var binding: FragmentEmailDetailBinding
    private var emailDetailsCallBack: EmailDetailsListener? = null
    private val viewModel by viewModel<EmailViewModel>()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEmailDetailBinding.bind(view)

        val emailContent = arguments?.getParcelable<EmailContent>(KEY_EMAIL_DETAIL_OBJECT)

        binding.btnClose.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        with(binding) {
            val userName = emailContent?.userName ?: emailContent?.recipient ?: ""
            val firstLetter = userName.take(1)
            ivUser.text = firstLetter
            this.tvUserName.text = userName

            tvSubject.text = emailContent?.subject
            tvEmail.text = emailContent?.body

            emailContent?.userImage?.let {
                ivUser.backgroundTintList = ColorStateList.valueOf(it)
            }

            btnStar.setOnClickListener {
                btnStar.setBackgroundResource(R.drawable.ic_star_dark_blue)
                emailContent?.let { viewModel.updateEmail(it) }
            }
        }

        binding.btnReplyAll.setOnClickListener {
            emailContent?.let {
                emailDetailsCallBack?.openCompose(
                    R.id.action_nav_email_detail_to_fragmentCompose,
                    it
                )
            }
        }
        binding.btnDelete.setOnClickListener {
            emailContent?.id?.let {
                viewModel.deleteEmail(it)
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EmailDetailsListener) {
            emailDetailsCallBack = context
        }
    }

    fun onBackPressed(): Boolean {
        return true
    }
}

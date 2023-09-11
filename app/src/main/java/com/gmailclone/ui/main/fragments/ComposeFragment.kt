package com.gmailclone.ui.main.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.gmailclone.R
import com.gmailclone.databinding.FragmentComposeBinding
import com.gmailclone.model.EmailContent
import com.gmailclone.ui.main.callbacks.EmailDetailsListener
import com.gmailclone.utils.Constants
import com.gmailclone.ui.main.viewmodel.EmailViewModel
import com.gmailclone.utils.isEmailValid
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.random.Random

class ComposeFragment : Fragment(R.layout.fragment_compose) {

    private lateinit var binding: FragmentComposeBinding
    private val user = FirebaseAuth.getInstance().currentUser
    private val viewModel by viewModel<EmailViewModel>()
    private var emailDetailsCallBack: EmailDetailsListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentComposeBinding.bind(view)

        val emailContent = arguments?.getParcelable<EmailContent>(Constants.KEY_EMAIL_DETAIL_OBJECT)

        with(binding) {
            editTextFrom.setText(user?.email)
            editTextTo.setText(emailContent?.recipient)
        }
        setupListener()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is EmailDetailsListener) {
            emailDetailsCallBack = context
        }
    }

    private fun setupListener() {
        val previousBackStackEntry = findNavController().previousBackStackEntry
        val previousDestinationId = previousBackStackEntry?.destination?.id

        binding.btnClose.setOnClickListener {

            when (previousDestinationId) {
                R.id.nav_email_detail -> {
                    findNavController().popBackStack()
                    emailDetailsCallBack?.closeCompose()
                }
                else -> {
                    emailDetailsCallBack?.closeEmailDetail()
                    findNavController().popBackStack()
                }
            }
        }
        binding.btnSend.setOnClickListener {
            sendEmail()
            when (previousDestinationId) {
                R.id.nav_email_detail -> {
                    findNavController().popBackStack()
                    emailDetailsCallBack?.closeCompose()
                }
                else -> {
                    emailDetailsCallBack?.closeEmailDetail()
                    findNavController().popBackStack()
                }
            }
        }
        binding.editTextTo.addTextChangedListener {
            if (isEmailValid(it.toString())) binding.btnSend.setBackgroundResource(R.drawable.ic_send_blue)
            else binding.btnSend.setBackgroundResource(R.drawable.ic_send_black)
        }

        if (isEmailValid(binding.editTextTo.text.toString())) binding.btnSend.setBackgroundResource(
            R.drawable.ic_send_blue
        )
        else binding.btnSend.setBackgroundResource(R.drawable.ic_send_black)
    }

    private fun sendEmail() {
        val sender = binding.editTextFrom.text.toString()
        val recipient = binding.editTextTo.text.toString()
        val subject = binding.editTextSubject.text.toString()
        val body = binding.editTextBody.text.toString()
        val timestamp = System.currentTimeMillis()

        val emailContent1 =
            arguments?.getParcelable<EmailContent>(Constants.KEY_EMAIL_DETAIL_OBJECT)

        val emailContent = EmailContent(
            emailContent1?.id,
            subject,
            sender,
            recipient,
            body,
            timestamp,
            userImage = emailContent1?.userImage ?: generateRandomColor(),
            userName = emailContent1?.userName,
            isSent = true
        )
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.sendEmail(emailContent)
        }
    }

 private   fun generateRandomColor(): Int {
        val colorOptions = listOf(
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.GRAY
        )
        val randomIndex = Random.nextInt(colorOptions.size)
        return colorOptions[randomIndex]
    }
}

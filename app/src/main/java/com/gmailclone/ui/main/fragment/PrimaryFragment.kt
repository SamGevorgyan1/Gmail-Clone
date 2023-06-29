package com.gmailclone.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmailclone.databinding.FragmentPrimaryBinding
import com.gmailclone.ui.compose.ComposeActivity


class PrimaryFragment : Fragment() {

    private lateinit var binding: FragmentPrimaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding=FragmentPrimaryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCompose.setOnClickListener {
                val intent = Intent(requireContext(), ComposeActivity::class.java)
                startActivity(intent)
        }
    }
}
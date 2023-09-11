package com.gmailclone.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.gmailclone.databinding.FragmentImportantBinding

class ImportantFragment:Fragment() {

    private lateinit var binding: FragmentImportantBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding=FragmentImportantBinding.inflate(inflater,container,false)
        return binding.root
    }
}
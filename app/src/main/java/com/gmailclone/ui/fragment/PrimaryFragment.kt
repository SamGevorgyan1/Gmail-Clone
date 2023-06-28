package com.gmailclone.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmailclone.databinding.FragmentPrimaryBinding


class PrimaryFragment : Fragment() {

    private lateinit var binding: FragmentPrimaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding=FragmentPrimaryBinding.inflate(inflater,container,false)
        return binding.root
    }


}
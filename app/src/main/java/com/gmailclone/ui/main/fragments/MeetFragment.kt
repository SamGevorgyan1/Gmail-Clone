package com.gmailclone.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.gmailclone.R
import com.gmailclone.databinding.FragmentMeetBinding
import com.gmailclone.ui.main.fragments.adapters.ImagePagerAdapter

class MeetFragment : Fragment(R.layout.fragment_meet) {

    private lateinit var binding: FragmentMeetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMeetBinding.bind(view)

        setupViewPager()
    }

    private fun setupViewPager() {
        val images = listOf(R.drawable.meet_image2, R.drawable.meet_image)
        val imagePagerAdapter = ImagePagerAdapter()
        imagePagerAdapter.update(images)

        binding.viewPager.adapter = imagePagerAdapter
        binding.circleIndicator.setViewPager(binding.viewPager)
    }
}

package com.tawajood.snail.ui.onboard.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentOnboardBinding
import com.tawajood.snail.ui.onboard.OnboardActivity


class FirstFragment : Fragment(R.layout.fragment_onboard) {

    private lateinit var binding: FragmentOnboardBinding
    private lateinit var parent: OnboardActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardBinding.bind(requireView())
        parent = requireActivity() as OnboardActivity

        setupUI()
    }

    private fun setupUI(){
        binding.tv1.text = getString(R.string.onboard_tv1)
        binding.tv2.text = getString(R.string.onboard_tv2)
        Glide.with(requireContext())
            .load(R.drawable.onboard_bg)
            .into(binding.bg)
        Glide.with(requireContext())
            .load(R.drawable.logo)
            .into(binding.logo)
        Glide.with(requireContext())
            .load(R.drawable.onboard1)
            .into(binding.img)
    }
}
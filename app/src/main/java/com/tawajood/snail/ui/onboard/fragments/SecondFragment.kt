package com.tawajood.snail.ui.onboard.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentOnboardBinding
import com.tawajood.snail.ui.onboard.OnboardActivity

class SecondFragment : Fragment(R.layout.fragment_onboard) {

    private lateinit var binding: FragmentOnboardBinding
    private lateinit var parent: OnboardActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOnboardBinding.bind(requireView())
        parent = requireActivity() as OnboardActivity

        setupUI()
    }

    private fun setupUI(){
        binding.tv1.text = getString(R.string.onboard_tv3)
        binding.tv2.text = getString(R.string.onboard_tv4)
        Glide.with(requireContext())
            .load(R.drawable.onboard_bg)
            .into(binding.bg)
        Glide.with(requireContext())
            .load(R.drawable.logo)
            .into(binding.logo)
        Glide.with(requireContext())
            .load(R.drawable.onboard2)
            .into(binding.img)
    }
}
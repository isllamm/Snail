package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentSettingsBinding
import com.tawajood.snail.databinding.FragmentTermsAndConditionsBinding
import com.tawajood.snail.ui.main.MainActivity


class TermsAndConditionsFragment : Fragment(R.layout.fragment_terms_and_conditions) {


    private lateinit var binding: FragmentTermsAndConditionsBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTermsAndConditionsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()

        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }


}
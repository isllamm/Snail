package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentHomeBinding
import com.tawajood.snail.databinding.FragmentSearchResultBinding
import com.tawajood.snail.ui.main.MainActivity


class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var parent: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()

        return binding.root
    }

    private fun onClick() {

    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }


}
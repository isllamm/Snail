package com.tawajood.snail.ui.main.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentPrevOrderInfoBinding
import com.tawajood.snail.databinding.FragmentPreviousConsultationBinding
import com.tawajood.snail.ui.main.MainActivity


class PrevOrderInfoFragment : Fragment(R.layout.fragment_prev_order_info) {


    private lateinit var binding: FragmentPrevOrderInfoBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrevOrderInfoBinding.bind(requireView())
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
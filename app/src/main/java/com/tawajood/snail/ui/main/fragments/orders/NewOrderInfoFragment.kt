package com.tawajood.snail.ui.main.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentNewConsultationBinding
import com.tawajood.snail.databinding.FragmentNewOrderInfoBinding
import com.tawajood.snail.ui.main.MainActivity

class NewOrderInfoFragment : Fragment(R.layout.fragment_new_order_info) {


    private lateinit var binding: FragmentNewOrderInfoBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewOrderInfoBinding.bind(requireView())
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
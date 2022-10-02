package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentCartBinding
import com.tawajood.snail.ui.main.MainActivity


class CartFragment : Fragment(R.layout.fragment_cart) {


    private lateinit var binding: FragmentCartBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()

        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            parent.navController.navigate(R.id.checkoutFragment)
        }
    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }


}
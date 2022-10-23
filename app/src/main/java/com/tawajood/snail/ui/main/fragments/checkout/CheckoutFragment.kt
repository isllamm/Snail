package com.tawajood.snail.ui.main.fragments.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentCheckoutBinding
import com.tawajood.snail.ui.main.MainActivity


class CheckoutFragment : Fragment(R.layout.fragment_checkout) {


    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckoutBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()

        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.btnCheckout.setOnClickListener { parent.navController.navigate(R.id.successfulOrderFromCartSheetFragment) }
    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }


}
package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentClinicInfoBinding
import com.tawajood.snail.databinding.FragmentPaymentBinding
import com.tawajood.snail.ui.main.MainActivity


class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()
    }

    private fun onClick() {
        binding.btnPay.setOnClickListener {
            parent.navController.navigate(R.id.action_paymentFragment_to_successfulPaymentSheetFragment)
        }
    }

    private fun setupUI() {

        parent.showBottomNav(false)

    }


}
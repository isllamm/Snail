package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentClinicInfoBinding
import com.tawajood.snail.databinding.FragmentHomeBinding
import com.tawajood.snail.ui.main.MainActivity


class ClinicInfoFragment : Fragment(R.layout.fragment_clinic_info) {
    private lateinit var binding: FragmentClinicInfoBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClinicInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        setupClinicRecycler()
        onClick()
    }

    private fun onClick() {
        binding.btnConsultationRequest.setOnClickListener {
            parent.navController.navigate(R.id.makeReservationFragment)
        }
        binding.btnReview.setOnClickListener {
            parent.navController.navigate(R.id.reviewSheetFragment)
        }
    }


    private fun setupClinicRecycler() {

    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }


}
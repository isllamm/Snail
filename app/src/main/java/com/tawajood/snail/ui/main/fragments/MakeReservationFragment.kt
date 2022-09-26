package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentClinicInfoBinding
import com.tawajood.snail.databinding.FragmentMakeReservationBinding
import com.tawajood.snail.ui.main.MainActivity


class MakeReservationFragment : Fragment(R.layout.fragment_make_reservation) {
    private lateinit var binding: FragmentMakeReservationBinding
    private lateinit var parent: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMakeReservationBinding.inflate(inflater)
        parent = requireActivity() as MainActivity

        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.cancelBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_makeReservationFragment_to_cancelReservationSheetFragment)
        }
        binding.nextBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_makeReservationFragment_to_successfulOrderSheetFragment)
        }
    }


}
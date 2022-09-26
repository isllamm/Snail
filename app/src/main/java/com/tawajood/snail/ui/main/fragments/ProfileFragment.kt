package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentProfileBinding
import com.tawajood.snail.databinding.FragmentReviewSheetBinding
import com.tawajood.snail.ui.main.MainActivity


class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var parent: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.editBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.changePasswordBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_profileFragment_to_otpToEditProfileInfoFragment)
        }
        binding.tvDeleteAccount.setOnClickListener {
            parent.navController.navigate(R.id.action_profileFragment_to_deleteAccountSheetFragment)
        }
    }


}
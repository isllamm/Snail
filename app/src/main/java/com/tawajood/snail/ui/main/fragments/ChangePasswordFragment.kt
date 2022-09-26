package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentChangePasswordBinding
import com.tawajood.snail.databinding.FragmentOtpToEditProfileInfoBinding
import com.tawajood.snail.ui.main.MainActivity


class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {


    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangePasswordBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        onClick()
    }

    private fun onClick() {
        binding.changeBtn.setOnClickListener {
            parent.navController.navigate(R.id.profileFragment)
        }
    }


}
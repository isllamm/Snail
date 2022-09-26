package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentEditProfileBinding
import com.tawajood.snail.databinding.FragmentProfileBinding
import com.tawajood.snail.ui.main.MainActivity


class EditProfileFragment : Fragment() {


    private lateinit var binding: FragmentEditProfileBinding
    private lateinit var parent: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        onClick()

        return binding.root
    }

    private fun onClick() {
        binding.editBtn.setOnClickListener {
            parent.navController.navigate(R.id.profileFragment)
        }
    }


}
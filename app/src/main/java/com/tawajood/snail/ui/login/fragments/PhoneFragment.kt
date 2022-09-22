package com.tawajood.snail.ui.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentPhoneBinding
import com.tawajood.snail.ui.login.LoginActivity

class PhoneFragment : Fragment(R.layout.fragment_phone) {

    private lateinit var binding: FragmentPhoneBinding
    private lateinit var parent: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhoneBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.forgot_password))
        parent.setToolbarSubtitle(getString(R.string.forgot_password_msg))
        parent.showBackImage(true)
    }

    private fun onClick(){
        binding.nextBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_phoneFragment_to_forgotPassVerificationFragment)
        }
    }
}
package com.tawajood.snail.ui.login.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentLoginBinding
import com.tawajood.snail.databinding.FragmentRegisterBinding
import com.tawajood.snail.ui.login.LoginActivity


class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var parent: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.welcome_in_snail))
        parent.setToolbarSubtitle(getString(R.string.new_register))
        parent.showBackImage(true)
    }

    private fun onClick(){
        binding.haveAnAccTv.setOnClickListener {
            parent.onBackPressed()
        }

        binding.registerBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_registerFragment_to_verificationFragment)
        }
    }
}
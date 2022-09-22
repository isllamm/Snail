package com.tawajood.snail.ui.login.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentLoginHomeBinding
import com.tawajood.snail.ui.login.LoginActivity


class LoginHomeFragment : Fragment(R.layout.fragment_login_home) {

    private lateinit var binding: FragmentLoginHomeBinding
    private lateinit var parent: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginHomeBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.welcome_in_snail))
        parent.setToolbarSubtitle(getString(R.string.login_type))
        parent.showBackImage(false)
    }

    private fun onClick(){
        binding.enterAsVisitorCard.setOnClickListener {
            parent.gotoMain()
        }

        binding.loginCard.setOnClickListener {
            parent.navController.navigate(R.id.action_loginHomeFragment_to_loginFragment)
        }

        binding.registerCard.setOnClickListener {
            parent.navController.navigate(R.id.action_loginHomeFragment_to_registerFragment)
        }
    }
}
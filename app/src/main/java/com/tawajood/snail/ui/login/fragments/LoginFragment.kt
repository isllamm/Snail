package com.tawajood.snail.ui.login.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentLoginBinding
import com.tawajood.snail.databinding.FragmentLoginHomeBinding
import com.tawajood.snail.ui.login.LoginActivity


class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var parent: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.welcome_in_snail))
        parent.setToolbarSubtitle(getString(R.string.login))
        parent.showBackImage(true)
    }

    private fun onClick(){
        binding.dontHaveAnAccTv.setOnClickListener {
            parent.navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotPasswordTv.setOnClickListener {
            parent.navController.navigate(R.id.action_loginFragment_to_phoneFragment)
        }

        binding.loginBtn.setOnClickListener {
            parent.gotoMain()
        }
    }

}
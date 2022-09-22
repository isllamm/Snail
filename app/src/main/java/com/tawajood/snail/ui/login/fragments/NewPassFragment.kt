package com.tawajood.snail.ui.login.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentNewPassBinding
import com.tawajood.snail.ui.login.LoginActivity


class NewPassFragment : Fragment(R.layout.fragment_new_pass) {

    private lateinit var binding: FragmentNewPassBinding
    private lateinit var parent: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewPassBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.new_password))
        parent.setToolbarSubtitle(getString(R.string.enter_new_password))
        parent.showBackImage(true)
    }

    private fun onClick(){
        binding.changeBtn.setOnClickListener {
            parent.gotoMain()
        }
    }
}
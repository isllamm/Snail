package com.tawajood.snail.ui.login.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentVerificationBinding
import com.tawajood.snail.ui.login.LoginActivity


class ForgotPassVerificationFragment : Fragment(R.layout.fragment_verification) {

    private lateinit var binding: FragmentVerificationBinding
    private lateinit var parent: LoginActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVerificationBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.verification_code))
        parent.setToolbarSubtitle(getString(R.string.verification_code_msg))
        parent.showBackImage(true)
    }

    private fun onClick(){
        binding.verCodeEt.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length == 4){
                    binding.activateBtn.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.beige)
                }else{
                    binding.activateBtn.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.off_white)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.activateBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_forgotPassVerificationFragment_to_newPassFragment)
        }
    }

}
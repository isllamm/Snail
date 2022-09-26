package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentOtpToEditProfileInfoBinding
import com.tawajood.snail.databinding.FragmentVerificationBinding
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.ui.main.MainActivity


class OtpToEditProfileInfoFragment : Fragment(R.layout.fragment_otp_to_edit_profile_info) {


    private lateinit var binding: FragmentOtpToEditProfileInfoBinding
    private lateinit var parent: MainActivity
    private var isDone: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOtpToEditProfileInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()
    }

    private fun onClick() {
        binding.verCodeEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length == 4) {
                    binding.activateBtn.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.beige)
                    isDone = true
                } else {
                    binding.activateBtn.backgroundTintList =
                        ContextCompat.getColorStateList(requireContext(), R.color.off_white)
                    isDone = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.activateBtn.setOnClickListener {
            if (isDone) {
                parent.navController.navigate(R.id.action_otpToEditProfileInfoFragment_to_changePasswordFragment)

            }
        }
    }

    private fun setupUI() {

    }


}
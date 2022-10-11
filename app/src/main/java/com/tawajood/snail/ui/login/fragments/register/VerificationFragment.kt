package com.tawajood.snail.ui.login.fragments.register

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentVerificationBinding
import com.tawajood.snail.pojo.RegisterBody
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class VerificationFragment : Fragment(R.layout.fragment_verification) {

    companion object {
        const val TAG = "RegVerificationFragment"
    }

    private lateinit var binding: FragmentVerificationBinding
    private lateinit var parent: LoginActivity
    private lateinit var phone: String
    private lateinit var countryCode: String
    private lateinit var verCode: String
    private lateinit var registerBody: RegisterBody
    private val viewModel: RegisterViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentVerificationBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        phone = requireArguments().getString(Constants.PHONE, "")
        countryCode = requireArguments().getString(Constants.COUNTRY_CODE, "")
        verCode = requireArguments().getString(Constants.VER_CODE, "")
        registerBody = requireArguments().getSerializable(Constants.REG_BODY) as RegisterBody

        observeData()
        setupUI()
        onClick()
    }

    private fun setupUI(){
        parent.setToolbarTitle(getString(R.string.verification_code))
        parent.setToolbarSubtitle(getString(R.string.verification_code_msg))
        parent.showBackImage(true)
    }

    private fun onClick(){
        binding.verCodeEt.addTextChangedListener(object: TextWatcher{
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
            viewModel.register(registerBody)

        }
    }


    private fun observeData(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.userFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val user = it.data!!
                        PrefsHelper.setToken(user.token)
                        PrefsHelper.setUserId(user.id)
                        PrefsHelper.setUsername(user.name)
                        PrefsHelper.setCountryCode(user.countryCode)
                        PrefsHelper.setPhone(user.phone)
                        PrefsHelper.setEmail(user.email)
                        PrefsHelper.setUserImage(user.image)
                        PrefsHelper.setCurrentLat(user.lat.toFloat())
                        PrefsHelper.setCurrentLng(user.lng.toFloat())
                        parent.gotoMain()
                    }
                }
            }
        }
    }

}
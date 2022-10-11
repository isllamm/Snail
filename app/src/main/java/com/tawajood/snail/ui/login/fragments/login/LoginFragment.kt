package com.tawajood.snail.ui.login.fragments.login

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentLoginBinding
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var parent: LoginActivity
    private val viewModel: LoginViewModel by viewModels()
    private var passIsShown = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(requireView())
        parent = requireActivity() as LoginActivity

        setupUI()
        onClick()
        observeData()
    }


    private fun setupUI() {
        parent.setToolbarTitle(getString(R.string.welcome_in_snail))
        parent.setToolbarSubtitle(getString(R.string.login))
        parent.showBackImage(true)
    }

    private fun onClick() {
        binding.eyeImage1.setOnClickListener {

            if (passIsShown) {
                passIsShown = false
                binding.passwordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye_closed)
                    .into(binding.eyeImage1)
            } else {
                passIsShown = true
                binding.passwordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye)
                    .into(binding.eyeImage1)
            }
            binding.passwordEt.typeface = ResourcesCompat.getFont(
                requireContext(),
                R.font.tajawal_regular
            )
            binding.passwordEt.setSelection(binding.passwordEt.length())

        }
        binding.dontHaveAnAccTv.setOnClickListener {
            parent.navController.navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.forgotPasswordTv.setOnClickListener {
            parent.navController.navigate(R.id.action_loginFragment_to_phoneFragment)
        }

        binding.loginBtn.setOnClickListener {
            if (validate()) {
                Log.i("isllam", "onClick: +i'm here1")
                viewModel.login(
                    binding.ccp.selectedCountryCode.toString(),
                    binding.phoneEt.text.toString(),
                    binding.passwordEt.text.toString()
                )
            }
        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.phoneEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.phone_is_required))

            return false
        }

        if (TextUtils.isEmpty(binding.passwordEt.text)) {

            ToastUtils.showToast(requireContext(), getString(R.string.password_is_required))

            return false
        }

        return true
    }

    private fun observeData() {
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
                        Log.i("isllam", "ana hena")
                        val user = it.data!!
                        PrefsHelper.setToken(user.token)
                        PrefsHelper.setUserId(user.id)
                        PrefsHelper.setUsername(user.name)
                        PrefsHelper.setCountryCode(user.countryCode)
                        PrefsHelper.setPhone(user.phone)
                        PrefsHelper.setUserImage(user.image)
                        PrefsHelper.setCurrentLat(user.lat.toFloat())
                        PrefsHelper.setCurrentLng(user.lng.toFloat())
                        parent.gotoMain()
                    }
                }
            }
        }
    }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            parent.showLoading()
            SmartLocation.with(requireContext()).location()
                .oneFix()
                .start {
                    PrefsHelper.setCurrentLat(it.latitude.toFloat())
                    PrefsHelper.setCurrentLng(it.longitude.toFloat())
                    parent.gotoMain()
                    Log.d("isllam", "lat: ${it.latitude}, lng: ${it.longitude}")
                }
        } else {
            Log.e("isllam", "onActivityResult: PERMISSION DENIED")
            ToastUtils.showToast(requireContext(), "Permission Denied")
        }
    }
}
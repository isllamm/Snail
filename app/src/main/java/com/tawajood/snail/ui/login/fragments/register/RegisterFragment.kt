package com.tawajood.snail.ui.login.fragments.register

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentRegisterBinding
import com.tawajood.snail.pojo.RegisterBody
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.util.regex.Pattern

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {
    companion object {
        private const val TAG = "RegisterFragment"
    }

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var parent: LoginActivity

    private lateinit var registerBody: RegisterBody

    private val viewModel: RegisterViewModel by viewModels()
    private var lat: Double? = null
    private var lng: Double? = null
    private var passIsShown = false
    private var verificationCode = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(requireView())
        parent = requireActivity() as LoginActivity
        observeData()
        setupUI()
        onClick()
    }

    private fun setupUI() {
        parent.setToolbarTitle(getString(R.string.welcome_in_snail))
        parent.setToolbarSubtitle(getString(R.string.new_register))
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

        binding.eyeImage2.setOnClickListener {
            if (passIsShown) {
                passIsShown = false
                binding.confirmPasswordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye_closed)
                    .into(binding.eyeImage2)
            } else {
                passIsShown = true
                binding.confirmPasswordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye)
                    .into(binding.eyeImage2)
            }
            binding.confirmPasswordEt.typeface = ResourcesCompat.getFont(
                requireContext(),
                R.font.tajawal_regular
            )
            binding.confirmPasswordEt.setSelection(binding.passwordEt.length())
        }

        binding.haveAnAccTv.setOnClickListener {
            parent.onBackPressed()
        }

        binding.registerBtn.setOnClickListener {
            if (validate()) {
                registerBody = RegisterBody(
                    binding.usernameEt.text.toString(),
                    binding.ccp.selectedCountryCode.toString(),
                    binding.phoneEt.text.toString(),
                    binding.emailEt.text.toString(),
                    binding.passwordEt.text.toString(),
                )
                viewModel.checkPhone(
                    binding.ccp.selectedCountryCode.toString(),
                    binding.phoneEt.text.toString()
                )

                viewModel.register(registerBody)
            }
        }
    }

    private fun validate(): Boolean {

        if (TextUtils.isEmpty(binding.usernameEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.name_is_required))

            return false
        }

        if (TextUtils.isEmpty(binding.phoneEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.phone_is_required))

            return false
        }


        if (TextUtils.isEmpty(binding.passwordEt.text)) {
            ToastUtils.showToast(requireContext(), getString(R.string.password_is_required))

            return false
        }
        if (TextUtils.isEmpty(binding.emailEt.text)) {

            return false
        }
        if (binding.passwordEt.text.toString() != binding.confirmPasswordEt.text.toString()) {
            ToastUtils.showToast(requireContext(), getString(R.string.password_not_match))

            return false
        }

        if (binding.passwordEt.text.toString().length < 6) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.short_pass)
            )
            return false
        }

        return true
    }

    private fun observeData() {

        lifecycleScope.launchWhenStarted {
            viewModel.checkPhone.collectLatest {
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
                        if (it.data!!.exists) {
                            ToastUtils.showToast(
                                requireContext(),
                                getString(R.string.phone_already_exists)
                            )
                        }
                    }
                }
            }
        }
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
                        PrefsHelper.setFirst(false)
                        parent.gotoMain()
                    }
                }
            }
        }



        /* lifecycleScope.launchWhenStarted {
             viewModel.sms.collectLatest {
                 parent.hideLoading()
                 when (it) {
                     is Resource.Error -> ToastUtils.showToast(
                         requireContext(),
                         getString(R.string.phone_num_problem)
                     )
                     is Resource.Idle -> {
                     }
                     is Resource.Loading -> parent.showLoading()
                     is Resource.Success -> {

                     }
                 }
             }
         }*/


    }

    private fun isValidEmailAddress(email: String?): Boolean {
        val ePattern =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = Pattern.compile(ePattern)
        val m = p.matcher(email.toString())
        return m.matches()
    }
    /*  private fun sendVerificationCode() {
          verificationCode = get6DigitsRandomNumber()
          Log.d(TAG, "sendVerificationCode: $verificationCode")
          viewModel.sendSms(
              SmsBody(
                  "Snail",
                  arrayListOf("${binding.ccp.selectedCountryCode}${binding.phoneEt.text}"),
                  "Your verification code is: $verificationCode"
              )
          )
      }

      private fun get6DigitsRandomNumber(): String {
          // It will generate 6 digit random Number.
          // from 0 to 999999
          val rnd = Random()
          val number: Int = rnd.nextInt(999999)
          // this will convert any number sequence into 6 character.
          return String.format(Locale.ENGLISH, "%06d", number)
      }*/


}
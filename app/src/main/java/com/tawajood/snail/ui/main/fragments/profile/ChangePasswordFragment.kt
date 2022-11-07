package com.tawajood.snail.ui.main.fragments.profile

import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentChangePasswordBinding
import com.tawajood.snail.databinding.FragmentOtpToEditProfileInfoBinding
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ChangePasswordFragment : Fragment(R.layout.fragment_change_password) {


    private lateinit var binding: FragmentChangePasswordBinding
    private lateinit var parent: MainActivity
    private val viewModel: ProfileViewModel by viewModels()
    private var passIsShown = false
    private var cPassIsShown = false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentChangePasswordBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        observeData()
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
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
            if (cPassIsShown) {
                cPassIsShown = false
                binding.confirmPasswordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye_closed)
                    .into(binding.eyeImage2)
            } else {
                cPassIsShown = true
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
            binding.confirmPasswordEt.setSelection(binding.confirmPasswordEt.length())
        }
        binding.eyeImage3.setOnClickListener {
            if (cPassIsShown) {
                cPassIsShown = false
                binding.oldPasswordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye_closed)
                    .into(binding.eyeImage3)
            } else {
                cPassIsShown = true
                binding.oldPasswordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                Glide.with(this)
                    .load(R.drawable.ic_eye)
                    .into(binding.eyeImage3)
            }
            binding.oldPasswordEt.typeface = ResourcesCompat.getFont(
                requireContext(),
                R.font.tajawal_regular
            )
            binding.oldPasswordEt.setSelection(binding.oldPasswordEt.length())
        }

        binding.changeBtn.setOnClickListener {
            if (validate()) {
                viewModel.changePassword(binding.passwordEt.text.toString())
            }
        }
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.passwordEt.text)) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.password_is_required)
            )
            return false
        }

        if (TextUtils.isEmpty(binding.confirmPasswordEt.text)) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.password_is_required)
            )
            return false
        }

        if (binding.passwordEt.text.toString() != binding.confirmPasswordEt.text.toString()) {
            ToastUtils.showToast(
                requireContext(),
                getString(R.string.password_not_match)
            )
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.changePassFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        ResultDialogFragment.newInstance(
                            getString(R.string.password_updated_successfully),
                            R.drawable.done
                        )
                            .show(
                                parentFragmentManager,
                                ResultDialogFragment::class.java.canonicalName
                            )
                    }
                }
            }
        }
    }

}
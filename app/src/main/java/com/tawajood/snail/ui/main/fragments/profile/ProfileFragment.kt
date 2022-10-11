package com.tawajood.snail.ui.main.fragments.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentProfileBinding
import com.tawajood.snail.databinding.FragmentReviewSheetBinding
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ProfileFragment : Fragment() {


    private lateinit var binding: FragmentProfileBinding
    private lateinit var parent: MainActivity
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        onClick()
        observeData()
        return binding.root
    }



    private fun onClick() {
        binding.editBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_profileFragment_to_editProfileFragment)
        }
        binding.changePasswordBtn.setOnClickListener {
            parent.navController.navigate(R.id.action_profileFragment_to_otpToEditProfileInfoFragment)
        }
        binding.tvDeleteAccount.setOnClickListener {
            parent.navController.navigate(R.id.action_profileFragment_to_deleteAccountSheetFragment)
        }
    }
    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.profileFlow.collectLatest {
                parent.hideLoading()
                when(it){
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        val profile = it.data!!
                        binding.tvName.text = profile.name
                        binding.ccp.setCountryForPhoneCode(profile.countryCode.toInt())
                        binding.phoneTv.text = profile.phone
                        Glide.with(requireContext())
                            .load(profile.image)
                            .into(binding.profileImg)
                    }
                }
            }
        }
    }

}
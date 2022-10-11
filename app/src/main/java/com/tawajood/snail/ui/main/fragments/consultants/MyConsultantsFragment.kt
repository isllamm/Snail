package com.tawajood.snail.ui.main.fragments.consultants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentMyConsultantsBinding
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyConsultantsFragment : Fragment(R.layout.fragment_my_consultants) {


    private lateinit var binding: FragmentMyConsultantsBinding
    private lateinit var parent: MainActivity
    private val viewModel: MyConsultantsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyConsultantsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        parent.showBottomNav(true)
    }

    private fun observeData(){
        lifecycleScope.launchWhenStarted {
            viewModel.consultantsFlow.collectLatest {
                parent.hideLoading()
                when(it){
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {parent.showLoading()}
                    is Resource.Success -> {

                    }
                }
            }
        }
    }
}
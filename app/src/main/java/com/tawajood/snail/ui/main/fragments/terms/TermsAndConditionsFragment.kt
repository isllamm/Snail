package com.tawajood.snail.ui.main.fragments.terms

import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentSettingsBinding
import com.tawajood.snail.databinding.FragmentTermsAndConditionsBinding
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TermsAndConditionsFragment : Fragment(R.layout.fragment_terms_and_conditions) {


    private lateinit var binding: FragmentTermsAndConditionsBinding
    private lateinit var parent: MainActivity
    private val viewModel: TermsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTermsAndConditionsBinding.bind(requireView())
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
        parent.showBottomNav(false)
    }

    @Suppress("DEPRECATION")
    private fun observeData(){
        lifecycleScope.launchWhenStarted {
            viewModel.termsFlow.collectLatest {
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
                        binding.terms.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            Html.fromHtml(it.data!!.terms, Html.FROM_HTML_MODE_COMPACT)
                        } else {
                            Html.fromHtml(it.data!!.terms)
                        }
                    }
                }
            }
        }
    }
}
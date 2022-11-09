package com.tawajood.snail.ui.main.fragments.consultants

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.MyNewConsultantsAdapter
import com.tawajood.snail.adapters.RecordedPetsAdapter
import com.tawajood.snail.adapters.TimesAdapter
import com.tawajood.snail.databinding.FragmentMyConsultantsBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.pojo.Times
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyConsultantsFragment : Fragment(R.layout.fragment_my_consultants) {

    private lateinit var newConsultantsAdapter: MyNewConsultantsAdapter
    private lateinit var binding: FragmentMyConsultantsBinding
    private lateinit var parent: MainActivity
    private var consultants = mutableListOf<Consultant>()
    private val viewModel: MyConsultantsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyConsultantsBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        parent.checkLogin()

        setupNewConsultantsRec()
        setupUI()
        observeData()
        onClick()
    }

    private fun setupNewConsultantsRec() {

        newConsultantsAdapter =
            MyNewConsultantsAdapter(object : MyNewConsultantsAdapter.OnItemClick {
                override fun onItemClickListener(position: Int) {
                    if (parent.checkLogin()) {
                        when (consultants[position].status) {
                            0 -> {
                                parent.navController.navigate(
                                    R.id.newConsultationFragment,
                                    bundleOf(Constants.REQUEST_ID to consultants[position].id)
                                )
                            }
                            1 -> {
                                parent.navController.navigate(
                                    R.id.pendingConsultationFragment,
                                    bundleOf(Constants.REQUEST_ID to consultants[position].id)
                                )
                            }
                            2 -> {
                                parent.navController.navigate(
                                    R.id.previousConsultationFragment,
                                    bundleOf(Constants.REQUEST_ID to consultants[position].id)
                                )
                            }
                        }

                    }

                }

            })

        binding.rvNewConsultation.adapter = newConsultantsAdapter
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        parent.showBottomNav(true)
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.consultantsFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {}
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        consultants = it.data!!.consultants

                        if (consultants.isEmpty()) {
                            binding.empty.isVisible = true
                            binding.tvEmpty.isVisible = true
                            binding.rvNewConsultation.isVisible = false
                        } else {
                            binding.empty.isVisible = false
                            binding.tvEmpty.isVisible = false
                            binding.rvNewConsultation.isVisible = true
                            newConsultantsAdapter.consultant = consultants

                        }

                    }
                }
            }
        }
    }
}
package com.tawajood.snail.ui.main.fragments.consultants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentNewConsultationBinding
import com.tawajood.snail.databinding.FragmentPendingConsultationBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class PendingConsultationFragment : Fragment(R.layout.fragment_pending_consultation) {


    private lateinit var binding: FragmentPendingConsultationBinding
    private lateinit var parent: MainActivity
    private var requestId by Delegates.notNull<Int>()
    private lateinit var consultant: Consultant
    private val viewModel: MyConsultantsViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPendingConsultationBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        requestId = requireArguments().getInt(Constants.REQUEST_ID)

        setupUI()
        observeData()
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.cancelBtn.setOnClickListener {
            viewModel.cancelConsultantById(requestId.toString())
        }
    }

    private fun setupUI() {
        viewModel.getConsultantById(requestId.toString())
        binding.nextBtn.isVisible = consultant.paid != 1
        parent.showBottomNav(false)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.consultantFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {

                        consultant = it.data!!.consultant

                        binding.tvStatus.text = consultant.statusName
                        binding.tvId.text = consultant.id.toString()
                        binding.orderDate.text = consultant.created_at
                        binding.tvName.text = consultant.clinic.name
                        binding.tvType.text = consultant.requestType.name
                        binding.tvSpecialty.text = consultant.specialization
                        binding.tvNameClinic.text = consultant.clinic.name

                        Glide.with(requireContext()).load(consultant.clinic.image_clinic)
                            .into(binding.clicnicImg)

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.cancelConsultantFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {

                        ResultDialogFragment.newInstance(
                            getString(R.string.done),
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
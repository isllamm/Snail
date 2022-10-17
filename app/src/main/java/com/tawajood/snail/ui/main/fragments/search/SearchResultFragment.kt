package com.tawajood.snail.ui.main.fragments.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ClinicsAdapter
import com.tawajood.snail.databinding.FragmentSearchResultBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding
    private lateinit var parent: MainActivity
    private var clinics = mutableListOf<Clinic>()
    private var clinicName by Delegates.notNull<String>()
    private lateinit var clinicsAdapter: ClinicsAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchResultBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        clinicName = requireArguments().getString(Constants.CLINIC_NAME).toString()
        viewModel.search(clinicName)

        setupUI()
        observeData()
        setupClinicsRecycler()
        return binding.root
    }

    private fun setupClinicsRecycler() {
        clinicsAdapter = ClinicsAdapter(object : ClinicsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                parent.navController.navigate(
                    R.id.clinicInfoFragment, bundleOf(
                        Constants.CLINIC to clinics[position].id
                    )
                )
            }

        })
        binding.rvResults.adapter = clinicsAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.clinicResultFlow.collectLatest {
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

                        clinics = it.data!!.data!!.clinics
                        clinicsAdapter.clinics = clinics

                    }
                }
            }
        }
    }

    private fun setupUI() {
        parent.showToolbar(true)
        parent.showBottomNav(false)
    }


}
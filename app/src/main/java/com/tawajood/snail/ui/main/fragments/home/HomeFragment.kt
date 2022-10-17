package com.tawajood.snail.ui.main.fragments.home

import android.Manifest
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ClinicsAdapter
import com.tawajood.snail.adapters.SliderAdapter
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentHomeBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.pojo.Slider
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.OnItemClickListener
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var parent: MainActivity
    private lateinit var clinicsAdapter: ClinicsAdapter
    private lateinit var sliderAdapter: SliderAdapter

    private var clinics = mutableListOf<Clinic>()
    private var sliderItems = mutableListOf<Slider>()
    private val viewModel: HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        Log.d("islam", "onViewCreated: "+PrefsHelper.getToken())
        setupUI()
        setupClinicsRecycler()
        setupTopClinics()
        onClick()
        observeData()
    }


    private fun setupUI() {
        parent.showToolbar(true)
        parent.showBottomNav(true)
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
        binding.clinicsRv.adapter = clinicsAdapter
    }

    private fun setupTopClinics() {

        sliderAdapter = SliderAdapter(object : SliderAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                parent.navController.navigate(
                    R.id.clinicInfoFragment, bundleOf(
                        Constants.CLINIC to clinics[position].id
                    )
                )
            }

        })
        binding.slider.setSliderAdapter(sliderAdapter)
        binding.slider.setIndicatorEnabled(true)
        binding.slider.startAutoCycle()

    }

    private fun onClick() {


        binding.locImg.setOnClickListener {
            if (!SmartLocation.with(requireContext()).location().state()
                    .locationServicesEnabled()
            ) {
                parent.navController.navigate(R.id.action_homeFragment_to_enableGpsSheet)
            } else {
                locationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        binding.filterIcon.setOnClickListener {
            parent.navController.navigate(R.id.action_homeFragment_to_searchSheet)
        }
        binding.searchIcon.setOnClickListener {
            parent.navController.navigate(
                R.id.searchResultFragment,
                bundleOf(Constants.CLINIC_NAME to binding.etSearch.text.toString())
            )
        }


    }

    private fun observeData() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.clinicFlow.collectLatest {
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

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.sliderFlow.collectLatest {
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

                        sliderItems = it.data!!.sliderItems
                        sliderAdapter.sliderItems = sliderItems

                    }
                }
            }
        }
    }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            parent.navController.navigate(R.id.action_homeFragment_to_mapFragment)
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            ToastUtils.showToast(requireContext(), "Permission Denied")
        }
    }


}
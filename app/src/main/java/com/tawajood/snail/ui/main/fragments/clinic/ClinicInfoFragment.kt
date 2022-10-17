package com.tawajood.snail.ui.main.fragments.clinic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ReviewsAdapter
import com.tawajood.snail.adapters.SliderAdapter
import com.tawajood.snail.adapters.SpecializationAdapter
import com.tawajood.snail.databinding.FragmentClinicInfoBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.pojo.Review
import com.tawajood.snail.pojo.Slider
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.main.fragments.home.HomeViewModel
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class ClinicInfoFragment : Fragment(R.layout.fragment_clinic_info) {
    private lateinit var binding: FragmentClinicInfoBinding
    private lateinit var parent: MainActivity
    private lateinit var reviewsAdapter: ReviewsAdapter
    private lateinit var specializationAdapter: SpecializationAdapter
    private lateinit var sliderItems: Slider
    private val review = mutableListOf<Review>()
    private var clinicId by Delegates.notNull<Int>()
    private val viewModel: ClinicViewModel by viewModels()
    private lateinit var clinic: Clinic

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClinicInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        clinicId = requireArguments().getInt(Constants.CLINIC)

        setupRecReviews()
        setupUI()
        setupRecSpecialization()
        observeData()
        onClick()
    }


    private fun setupRecSpecialization() {
        specializationAdapter = SpecializationAdapter()
        binding.rvSpecialization.adapter = specializationAdapter
    }
    private fun setupRecReviews() {
        reviewsAdapter = ReviewsAdapter()
        binding.rvReviews.adapter = reviewsAdapter
    }

    private fun onClick() {
        binding.btnConsultationRequest.setOnClickListener {
            parent.navController.navigate(
                R.id.makeReservationFragment,
                bundleOf(Constants.CLINIC to clinicId)
            )
        }
        binding.btnReview.setOnClickListener {
            parent.navController.navigate(
                R.id.reviewSheetFragment,
                bundleOf(Constants.REVIEW_CLINIC to clinicId)
            )
        }
    }


    private fun setupUI() {
        parent.showBottomNav(false)
        viewModel.getClinicInfo(clinicId.toString())


    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.clinicInfoFlow.collectLatest {
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

                        clinic = it.data!!.data!!.clinics[0]
                        Log.d("islam", "observeData: " + clinic.name)
                        Log.d(
                            "islam",
                            "observeData: " + clinic.specializations[0].specialization.name
                        )
                        binding.tvClinicName.text = clinic.name
                        binding.ratingBar.rating = clinic.rating.toFloat()
                        binding.tvDescription.text = clinic.details
                        binding.tvAddress.text = clinic.address
                        binding.tvNum.text = clinic.registration_number
                        Glide.with(requireContext()).load(clinic.image).into(binding.clinicImg)
                        specializationAdapter.specialization = clinic.specializations
                        reviewsAdapter.reviews = clinic.recommendations
                        //binding.tvSpecialties.text = clinic.specialization.specialization.name
                    }
                }
            }
        }
    }
}
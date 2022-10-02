package com.tawajood.snail.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ClinicsAdapter
import com.tawajood.snail.adapters.ReviewsAdapter
import com.tawajood.snail.adapters.SliderAdapter
import com.tawajood.snail.databinding.FragmentClinicInfoBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.pojo.Review
import com.tawajood.snail.pojo.Slider
import com.tawajood.snail.ui.main.MainActivity


class ClinicInfoFragment : Fragment(R.layout.fragment_clinic_info) {
    private lateinit var binding: FragmentClinicInfoBinding
    private lateinit var parent: MainActivity
    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var reviewsAdapter: ReviewsAdapter
    private val sliderItems = mutableListOf<Slider>()
    private val review = mutableListOf<Review>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentClinicInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        setupSlider()
        setupRec()
        onClick()
    }

    private fun setupRec() {
        review.add(Review("احمد محمد", "جميل",4f, R.drawable.profile))
        review.add(Review("محمد", "رائع",3f, R.drawable.profile))
        review.add(Review("محمود", "مدهش",5f, R.drawable.profile))
        reviewsAdapter = ReviewsAdapter()
        reviewsAdapter.reviews = review
        binding.rvReviews.adapter = reviewsAdapter
    }

    private fun onClick() {
        binding.btnConsultationRequest.setOnClickListener {
            parent.navController.navigate(R.id.makeReservationFragment)
        }
        binding.btnReview.setOnClickListener {
            parent.navController.navigate(R.id.reviewSheetFragment)
        }
    }


    private fun setupSlider() {
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderAdapter = SliderAdapter()
        sliderAdapter.sliderItems = sliderItems
        binding.slider.setSliderAdapter(sliderAdapter)
    }

    private fun setupUI() {
        parent.showBottomNav(false)


    }


}
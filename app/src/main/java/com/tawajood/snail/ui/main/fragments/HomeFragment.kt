package com.tawajood.snail.ui.main.fragments

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ClinicsAdapter
import com.tawajood.snail.adapters.SliderAdapter
import com.tawajood.snail.databinding.FragmentHomeBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.pojo.Slider
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.ToastUtils
import io.nlopez.smartlocation.SmartLocation

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var parent: MainActivity
    private lateinit var clinicsAdapter: ClinicsAdapter
    private lateinit var sliderAdapter: SliderAdapter

    private val clinics = mutableListOf<Clinic>()
    private val sliderItems = mutableListOf<Slider>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        setupClinicsRecycler()
        setupTopClinics()
        onClick()
    }

    private fun setupUI(){
        parent.showToolbar(true)
        parent.showBottomNav(true)
    }

    private fun setupClinicsRecycler(){
        clinics.add(Clinic("فيتنيس كلينك", 4f, R.drawable.test1))
        clinics.add(Clinic("دكتور زوو", 3f, R.drawable.test2))
        clinics.add(Clinic("سول كلينك", 5f, R.drawable.test3))
        clinics.add(Clinic("فيتولوجي", 3.5f, R.drawable.test4))
        clinicsAdapter = ClinicsAdapter()
        clinicsAdapter.clinics = clinics
        binding.clinicsRv.adapter = clinicsAdapter
    }

    private fun setupTopClinics(){
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderItems.add(Slider("https://www.fischerveterinaryclinic.com//images/home/building-2.jpg"))
        sliderAdapter = SliderAdapter()
        sliderAdapter.sliderItems = sliderItems
        binding.slider.setSliderAdapter(sliderAdapter)
    }

    private fun onClick(){
        binding.locImg.setOnClickListener {
            if (!SmartLocation.with(requireContext()).location().state().locationServicesEnabled()) {
                parent.navController.navigate(R.id.action_homeFragment_to_enableGpsSheet)
            } else {
                locationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }

        binding.filterIcon.setOnClickListener {
            parent.navController.navigate(R.id.action_homeFragment_to_searchSheet)
        }
    }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            parent.navController.navigate(R.id.action_homeFragment_to_mapFragment)
        } else {
            Log.e("7ima", "onActivityResult: PERMISSION DENIED")
            ToastUtils.showToast(requireContext(), "Permission Denied")
        }
    }

}
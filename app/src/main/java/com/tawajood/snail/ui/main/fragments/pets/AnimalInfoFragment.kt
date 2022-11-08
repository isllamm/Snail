package com.tawajood.snail.ui.main.fragments.pets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.adapters.MedicineAdapter
import com.tawajood.snail.adapters.PreviousReportsAdapter
import com.tawajood.snail.adapters.VaccinationsAdapter
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentAnimalInfoBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.pojo.Pet
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class AnimalInfoFragment : Fragment(R.layout.fragment_animal_info) {


    private lateinit var binding: FragmentAnimalInfoBinding
    private lateinit var parent: MainActivity
    private var petId by Delegates.notNull<Int>()
    private val viewModel: PetsViewModel by viewModels()
    private lateinit var pet: Pet
    private lateinit var consultants: MutableList<Consultant>
    private lateinit var previousReportsAdapter: PreviousReportsAdapter
    private lateinit var medicineAdapter: MedicineAdapter
    private lateinit var vaccinationsAdapter: VaccinationsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimalInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        petId = requireArguments().getInt(Constants.PET_ID)
        setupUI()
        observeData()
        onClick()
        setupPrevRec()
        setupMed()
        setupVac()
    }

    private fun setupVac() {
        vaccinationsAdapter = VaccinationsAdapter(object : VaccinationsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {

            }

        })
        binding.rvVaccination.adapter = vaccinationsAdapter
    }

    private fun setupMed() {
        medicineAdapter = MedicineAdapter(object : MedicineAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {

            }

        })
        binding.rvDescription.adapter = medicineAdapter
    }

    private fun setupPrevRec() {
        previousReportsAdapter =
            PreviousReportsAdapter(object : PreviousReportsAdapter.OnItemClick {
                override fun onItemClickListener(position: Int) {

                }

            })

        binding.rvPreviousReports.adapter = previousReportsAdapter
    }


    private fun onClick() {
        binding.ivBack.setOnClickListener {

            parent.onBackPressed()
        }
        binding.addBtn.setOnClickListener {
            parent.navController.navigate(
                R.id.addInoculationFragment,
                bundleOf(Constants.PET_ID to petId)
            )
        }
    }

    private fun setupUI() {
        viewModel.getPetById(petId.toString())
        parent.showBottomNav(false)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.myPetFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        pet = it.data!!.pet
                        binding.tvName.text = pet.name

                        consultants = pet.requests
                        if (consultants.isEmpty()) {
                            binding.rvPreviousReports.isVisible = false
                            binding.rvVaccination.isVisible = false
                            binding.empty.isVisible = true
                            binding.i1.isVisible = false
                            binding.tv1.isVisible = false
                            binding.i2.isVisible = false
                            binding.tv2.isVisible = false
                        } else {
                            binding.rvPreviousReports.isVisible = true
                            binding.rvVaccination.isVisible = true
                            binding.empty.isVisible = false
                            binding.i1.isVisible = true
                            binding.tv1.isVisible = true
                            binding.i2.isVisible = true
                            binding.tv2.isVisible = true
                            previousReportsAdapter.consultant = consultants
                            medicineAdapter.consultant = consultants
                        }
                        if (pet.vaccinations.isEmpty()) {
                            binding.empty1.isVisible = true
                            binding.rvVaccination.isVisible = false
                            binding.i3.isVisible = false
                            binding.tv3.isVisible = false
                        } else {
                            binding.empty1.isVisible = false
                            binding.rvVaccination.isVisible = true
                            binding.i3.isVisible = true
                            binding.tv3.isVisible = true
                            vaccinationsAdapter.petVaccinations = pet.vaccinations

                        }
                        Glide.with(requireContext())
                            .load(pet.image)
                            .into(binding.imgIv)
                    }
                }
            }
        }

    }

}
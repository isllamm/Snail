package com.tawajood.snail.ui.main.fragments.pets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentAnimalInfoBinding
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimalInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        petId = requireArguments().getInt(Constants.PET_ID)
        setupUI()
        observeData()
        onClick()
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
                        Glide.with(requireContext())
                            .load(pet.image)
                            .into(binding.imgIv)
                    }
                }
            }
        }

    }

}
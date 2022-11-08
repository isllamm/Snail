package com.tawajood.snail.ui.main.fragments.pets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ClinicsAdapter
import com.tawajood.snail.adapters.MyPetsAdapter
import com.tawajood.snail.adapters.SliderAdapter
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentMyAnimalsBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.pojo.Pet
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyAnimalsFragment : Fragment(R.layout.fragment_my_animals) {

    private lateinit var binding: FragmentMyAnimalsBinding
    private lateinit var parent: MainActivity
    private val viewModel: PetsViewModel by viewModels()
    private lateinit var petsAdapter: MyPetsAdapter
    private var pets = mutableListOf<Pet>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyAnimalsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupPetsRecycler()
        setupUI()
        observeData()
        onClick()
    }

    private fun setupPetsRecycler() {
        petsAdapter = MyPetsAdapter(object : MyPetsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                parent.navController.navigate(
                    R.id.animalInfoFragment, bundleOf(
                        Constants.PET_ID to pets[position].id
                    )
                )
            }

        })

        binding.rvMyAnimals.adapter = petsAdapter
    }


    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.addAnimalBtn.setOnClickListener {
            parent.navController.navigate(R.id.addNewAnimalFragment)
        }
    }

    private fun setupUI() {
        viewModel.getPets()
        parent.showBottomNav(false)
    }

    private fun observeData() {


        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.myPetsFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        Log.d("islam", "observeData: " + "loading")
                        parent.showLoading()
                    }
                    is Resource.Success -> {

                        pets = it.data!!.pets
                        if (pets.isEmpty()) {
                            binding.rvMyAnimals.isVisible = false
                            binding.empty.isVisible = true
                            binding.tvEmpty.isVisible = true
                        } else {
                            binding.rvMyAnimals.isVisible = true
                            binding.empty.isVisible = false
                            binding.tvEmpty.isVisible = false
                            petsAdapter.pets = pets

                        }

                    }
                }
            }
        }

    }
}
package com.tawajood.snail.ui.main.fragments.pets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAboutUsBinding
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.pojo.PetType
import com.tawajood.snail.pojo.VaccinationTypes
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class AddInoculationFragment : Fragment(R.layout.fragment_add_inoculation) {

    private val viewModel: PetsViewModel by viewModels()
    private lateinit var binding: FragmentAddInoculationBinding
    private lateinit var parent: MainActivity
    private lateinit var vacAdapter: ArrayAdapter<String>
    private var types = mutableListOf<VaccinationTypes>()

    private var petId by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddInoculationBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        petId = requireArguments().getInt(Constants.PET_ID)

        setupUI()
        observeData()
        onClick()
        setupTypes()
    }


    private fun setupTypes() {
        vacAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.typeSpinner.adapter = vacAdapter
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }

        binding.nextBtn.setOnClickListener {
            viewModel.addVaccination(
                petId.toString(),
                binding.dateEt.text.toString(),
                types[binding.typeSpinner.selectedItemPosition].id.toString(),
            )
        }
    }

    private fun setupUI() {
        viewModel.getVaccinationTypes()
        parent.showBottomNav(false)
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.vaccinationTypesFlow.collectLatest {
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
                        types = it.data!!.vaccinationTypes
                        types.forEach { types ->
                            vacAdapter.add(types.name)
                        }
                        Log.d("islam", "observeData: " + types[0].name)

                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.addVaccinationFlow.collectLatest {
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
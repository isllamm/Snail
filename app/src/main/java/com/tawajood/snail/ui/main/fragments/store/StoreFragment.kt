package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.CategoriesAdapter
import com.tawajood.snail.adapters.ClinicsAdapter
import com.tawajood.snail.databinding.FragmentSettingsBinding
import com.tawajood.snail.databinding.FragmentStoreBinding
import com.tawajood.snail.pojo.Categories
import com.tawajood.snail.pojo.Day
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class StoreFragment : Fragment(R.layout.fragment_store) {


    private val viewModel: StoreViewModel by viewModels()
    private lateinit var binding: FragmentStoreBinding
    private lateinit var parent: MainActivity
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var cats = mutableListOf<Categories>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoreBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()

        setupRec()
        observeData()
        onClick()
    }

    private fun setupRec() {
        categoriesAdapter = CategoriesAdapter(object : CategoriesAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                Log.d("islam", "onItemClickListener: 111")
                parent.navController.navigate(
                    R.id.animalStoreFragment, bundleOf(
                        Constants.CAT_ID to cats[position].id
                    )
                )
            }

        })
        binding.rvCats.adapter = categoriesAdapter
    }


    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.cardStore.setOnClickListener {
            parent.navController.navigate(R.id.action_storeFragment_to_animalStoreFragment)
        }

        binding.cardPharmacy.setOnClickListener {
            parent.navController.navigate(R.id.action_storeFragment_to_animalStoreFragment)
        }
    }

    private fun setupUI() {
        parent.showBottomNav(true)
    }

    private fun observeData() {

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.categoriesFlow.collectLatest {
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

                        cats = it.data!!.cats
                        categoriesAdapter.cats = cats

                    }
                }
            }
        }
    }
}
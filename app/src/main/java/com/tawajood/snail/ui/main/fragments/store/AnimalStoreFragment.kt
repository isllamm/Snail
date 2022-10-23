package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.CategoriesAdapter
import com.tawajood.snail.adapters.VendorsAdapter
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentAnimalStoreBinding
import com.tawajood.snail.pojo.Categories
import com.tawajood.snail.pojo.Store
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class AnimalStoreFragment : Fragment(R.layout.fragment_animal_store) {

    private val viewModel: StoreViewModel by viewModels()
    private lateinit var binding: FragmentAnimalStoreBinding
    private lateinit var parent: MainActivity
    private var catId by Delegates.notNull<Int>()
    private lateinit var vendorsAdapter: VendorsAdapter
    private var vendors = mutableListOf<Store>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAnimalStoreBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        catId = requireArguments().getInt(Constants.CAT_ID)

        setupUI()
        setupVendorsRec()
        observeData()
        onClick()
    }


    private fun setupVendorsRec() {
        vendorsAdapter = VendorsAdapter(object : VendorsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                parent.navController.navigate(
                    R.id.subCategoriesFragment,
                    bundleOf(Constants.VENDOR_ID to vendors[position].id)
                )
            }

        })
        binding.rvVendors.adapter = vendorsAdapter
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        binding.tv.text = "البائعين"
        viewModel.getVendorsByCategoryId(catId.toString())
        parent.showBottomNav(false)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.vendorsFlow.collectLatest {
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
                        vendors = it.data!!.vendors.data
                        vendorsAdapter.vendors = vendors
                    }
                }
            }
        }
    }
}
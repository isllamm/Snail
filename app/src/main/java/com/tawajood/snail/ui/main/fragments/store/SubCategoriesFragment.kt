package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ProductsAdapter
import com.tawajood.snail.adapters.SubCategoriesAdapter
import com.tawajood.snail.adapters.VendorsAdapter
import com.tawajood.snail.databinding.FragmentAnimalStoreBinding
import com.tawajood.snail.databinding.FragmentStoreBinding
import com.tawajood.snail.databinding.FragmentSubCategoriesBinding
import com.tawajood.snail.pojo.Product
import com.tawajood.snail.pojo.Store
import com.tawajood.snail.pojo.SubCategory
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.OnItemClickListener
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class SubCategoriesFragment : Fragment(R.layout.fragment_sub_categories) {

    private val viewModel: StoreViewModel by viewModels()
    private lateinit var binding: FragmentSubCategoriesBinding
    private lateinit var parent: MainActivity

    private lateinit var productsAdapter: ProductsAdapter
    private var products = mutableListOf<Product>()
    private var subCats = mutableListOf<SubCategory>()
    private var vendorId by Delegates.notNull<Int>()
    private lateinit var secAdapter: ArrayAdapter<String>


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubCategoriesBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        vendorId = requireArguments().getInt(Constants.VENDOR_ID)

        setupUI()
        setupSubRec()
        observeData()
        setupProdRec()
        onClick()
    }

    private fun onClick() {
        binding.sectionsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.getProducts(subCats[position].id.toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        //viewModel.getProducts("1")
        viewModel.getSubCategory(vendorId.toString())
        parent.showBottomNav(false)
    }

    private fun setupSubRec() {
        secAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        binding.sectionsSpinner.adapter = secAdapter

    }

    private fun setupProdRec() {

        productsAdapter = ProductsAdapter(object : ProductsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                parent.navController.navigate(
                    R.id.productInfoFragment,
                    bundleOf(Constants.PRODUCT_ID to products[position].id)
                )
            }

        })
        binding.rvSub.adapter = productsAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.subCategoriesFlow.collectLatest {
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
                        subCats = it.data!!.subcategories
                        subCats.forEach { subCats -> secAdapter.add(subCats.name) }
                        if (subCats.isNotEmpty()) {
                            viewModel.getProducts(subCats[0].id.toString())
                        }
                        //Log.d("islam", "observeData: "+subCats[binding.sectionsSpinner.selectedItemPosition].id)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.productsFlow.collectLatest {

                parent.hideLoading()
                when (it) {
                    is Resource.Error -> {
                        ToastUtils.showToast(requireContext(), it.message.toString())
                        productsAdapter.products = mutableListOf()
                        binding.empty.isVisible = true

                    }
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        products = it.data!!.products.data
                        if (products.isEmpty()) {
                            binding.empty.isVisible = true
                            binding.rvSub.isVisible = false
                        } else {
                            binding.empty.isVisible = false
                            binding.rvSub.isVisible = true
                            productsAdapter.products = products

                        }
                    }
                }
            }
        }
    }


}


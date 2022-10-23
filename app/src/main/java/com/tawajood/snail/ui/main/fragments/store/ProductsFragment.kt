package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ProductsAdapter
import com.tawajood.snail.databinding.FragmentProductsBinding
import com.tawajood.snail.pojo.Product
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private val viewModel: StoreViewModel by viewModels()
    private lateinit var binding: FragmentProductsBinding
    private lateinit var parent: MainActivity
    private lateinit var productsAdapter: ProductsAdapter
    private var products = mutableListOf<Product>()

    private var subCatId by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductsBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        subCatId = requireArguments().getInt(Constants.SUB_CAT_ID)
        Log.d("islam", "getProducts: $subCatId")


        setupUI()
        setupSubRec()
        observeData()
        onClick()
    }


    private fun setupSubRec() {

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

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {

        binding.tv.text = "المنتجات"
        viewModel.getProducts(subCatId.toString())
        parent.showBottomNav(false)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.productsFlow.collectLatest {

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
                        products = it.data!!.products.data
                        productsAdapter.products = products
                    }
                }
            }
        }
    }

}
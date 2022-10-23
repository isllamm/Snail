package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.ProductSliderAdapter
import com.tawajood.snail.databinding.FragmentPrevOrderInfoBinding
import com.tawajood.snail.databinding.FragmentProductInfoBinding
import com.tawajood.snail.pojo.Product
import com.tawajood.snail.pojo.ProductImages
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class ProductInfoFragment : Fragment(R.layout.fragment_product_info) {

    private val viewModel: StoreViewModel by viewModels()
    private lateinit var binding: FragmentProductInfoBinding
    private lateinit var parent: MainActivity
    private var products = mutableListOf<Product>()
    private var images = mutableListOf<ProductImages>()
    private lateinit var productSliderAdapter: ProductSliderAdapter
    private var productId by Delegates.notNull<Int>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        productId = requireArguments().getInt(Constants.PRODUCT_ID)

        setupUI()
        setupSlider()
        observeData()
        onClick()
    }

    private fun setupSlider() {
        productSliderAdapter = ProductSliderAdapter(object : ProductSliderAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {

            }

        })

        binding.slider.setSliderAdapter(productSliderAdapter)
        binding.slider.setIndicatorEnabled(true)
        binding.slider.startAutoCycle()
    }


    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }

        binding.remove.setOnClickListener {
            var c: Int = binding.counter.text.toString().toInt()
            if (c >= 2) {
                c -= 1
                binding.counter.text = c.toString()
            }
        }

        binding.add.setOnClickListener {
            var c: Int = binding.counter.text.toString().toInt()
            c += 1
            binding.counter.text = c.toString()
        }
        binding.ivCart.setOnClickListener { parent.navController.navigate(R.id.cartFragment) }
        binding.btnAddToCart.setOnClickListener {
            viewModel.addToCart(productId.toString(), binding.counter.text.toString())
        }
    }

    private fun setupUI() {
        viewModel.getProductById(productId.toString())
        parent.showBottomNav(false)


    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.productByIdFlow.collectLatest {

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
                        products = it.data!!.products
                        images = products[0].images
                        if (images.isNotEmpty()) {
                            productSliderAdapter.sliderItems = images
                        }
                        binding.tvProductName.text = products[0].name
                        binding.tvPrice.text = products[0].price.toString() + "$"
                        binding.tvDescription.text = products[0].desc

                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.addToCartFlow.collectLatest {

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

                        ToastUtils.showToast(requireContext(), it.message.toString())
                    }
                }
            }
        }
    }
}
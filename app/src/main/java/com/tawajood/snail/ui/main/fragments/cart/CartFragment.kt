package com.tawajood.snail.ui.main.fragments.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.CartAdapter
import com.tawajood.snail.adapters.VendorsAdapter
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentCartBinding
import com.tawajood.snail.pojo.Cart
import com.tawajood.snail.pojo.CartResponse
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.main.fragments.store.StoreViewModel
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val viewModel: CartViewModel by viewModels()
    private lateinit var binding: FragmentCartBinding
    private lateinit var parent: MainActivity
    private lateinit var cartAdapter: CartAdapter
    private var cartItems = mutableListOf<Cart>()
    private lateinit var cartResponse: CartResponse

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        setupRec()
        onClick()
        observeData()
    }


    private fun setupRec() {
        cartAdapter = CartAdapter(object : CartAdapter.OnItemClick {
            override fun onDeleteClickListener(position: Int) {
                viewModel.deleteItemFromCart(cartItems[position].id)

            }

            override fun onPlusClickListener(position: Int) {
                viewModel.updateCart(cartItems[position].id, cartItems[position].quantity + 1)

            }

            override fun onMinusClickListener(position: Int) {
                if (cartItems[position].quantity > 1) {
                    viewModel.updateCart(cartItems[position].id, cartItems[position].quantity - 1)
                }
            }
        })

        binding.rvCartProducts.adapter = cartAdapter
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            parent.navController.navigate(R.id.checkoutFragment)
        }
    }

    private fun setupUI() {

        parent.showBottomNav(false)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.getCartFlow.collectLatest {

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
                        cartResponse = it.data!!
                        cartItems = it.data.carts

                        cartAdapter.cart = cartItems

                        binding.tvTotalPrice.text = "ريال" + cartResponse.finalTotal
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.deleteFlow.collectLatest {
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        parent.viewModel.setCartCount(PrefsHelper.getCartCount())
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.updateFlow.collectLatest {
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {}
                }
            }
        }
    }

}
package com.tawajood.snail.ui.main.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.CartAdapter
import com.tawajood.snail.adapters.OrdersAdapter
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentMyOrdersBinding
import com.tawajood.snail.pojo.Cart
import com.tawajood.snail.pojo.Order
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.main.fragments.cart.CartViewModel
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MyOrdersFragment : Fragment(R.layout.fragment_my_orders) {

    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var binding: FragmentMyOrdersBinding
    private lateinit var parent: MainActivity
    private lateinit var ordersAdapter: OrdersAdapter
    private var orders = mutableListOf<Order>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyOrdersBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        setupOrders()
        observeData()
        onClick()
    }


    private fun setupOrders() {
        ordersAdapter = OrdersAdapter(object : OrdersAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                if (orders[position].status == 3) {
                    parent.navController.navigate(
                        R.id.prevOrderInfoFragment,
                        bundleOf(Constants.ORDER_ID to orders[position].id)
                    )
                } else {
                    parent.navController.navigate(
                        R.id.newOrderInfoFragment,
                        bundleOf(Constants.ORDER_ID to orders[position].id)
                    )
                }
            }

        })
        binding.newOrders.adapter = ordersAdapter
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }

    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.getOrdersFlow.collectLatest {

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
                        orders = it.data!!.orders
                        ordersAdapter.orders = orders
                    }
                }
            }
        }
    }
}
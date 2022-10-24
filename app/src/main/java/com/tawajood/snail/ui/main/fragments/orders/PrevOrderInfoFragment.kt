package com.tawajood.snail.ui.main.fragments.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.CheckoutItemsAdapter
import com.tawajood.snail.databinding.FragmentPrevOrderInfoBinding
import com.tawajood.snail.databinding.FragmentPreviousConsultationBinding
import com.tawajood.snail.pojo.Cart
import com.tawajood.snail.pojo.Order
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class PrevOrderInfoFragment : Fragment(R.layout.fragment_prev_order_info) {


    private lateinit var binding: FragmentPrevOrderInfoBinding
    private lateinit var parent: MainActivity
    private var orderId by Delegates.notNull<Int>()
    private lateinit var order: Order
    private val viewModel: OrdersViewModel by viewModels()
    private lateinit var checkoutItemsAdapter: CheckoutItemsAdapter
    private var cartItems = mutableListOf<Cart>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPrevOrderInfoBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        orderId = requireArguments().getInt(Constants.ORDER_ID)

        observeData()
        setupUI()
        setupRec()
        onClick()
    }

    private fun setupRec() {
        checkoutItemsAdapter = CheckoutItemsAdapter(object : CheckoutItemsAdapter.OnItemClick {

        })

        binding.rvCheckoutProducts.adapter = checkoutItemsAdapter
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.orderFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {}
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        order = it.data!!.order

                        binding.addressTv.text = order.address
                        binding.usernameTv.text = order.name
                        binding.phoneTv.text = order.user_phone
                        binding.ccp.textView_selectedCountry.text = order.country_code
                        cartItems = order.carts!!
                        checkoutItemsAdapter.cart = cartItems
                        binding.paymentType.text = order.payment_method.toString()
                        binding.tvTotalPrice.text = order.total.toString()
                        binding.tvDeliveryPrice.text = order.delivery_cost.toString()
                        binding.tvTotal.text = order.final_total.toString()


                    }
                }
            }
        }


    }

    private fun onClick() {

        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        viewModel.getOrderById(orderId.toString())
        parent.showBottomNav(false)
    }


}
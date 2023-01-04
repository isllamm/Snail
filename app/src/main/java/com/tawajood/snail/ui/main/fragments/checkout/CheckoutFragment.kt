package com.tawajood.snail.ui.main.fragments.checkout

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.myfatoorah.sdk.entity.executepayment.MFExecutePaymentRequest
import com.myfatoorah.sdk.entity.paymentstatus.MFGetPaymentStatusResponse
import com.myfatoorah.sdk.utils.MFAPILanguage
import com.myfatoorah.sdk.utils.MFBaseURL
import com.myfatoorah.sdk.utils.MFCountry
import com.myfatoorah.sdk.utils.MFEnvironment
import com.myfatoorah.sdk.views.MFResult
import com.myfatoorah.sdk.views.MFSDK
import com.tawajood.snail.R
import com.tawajood.snail.adapters.CartAdapter
import com.tawajood.snail.adapters.CheckoutItemsAdapter
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentCheckoutBinding
import com.tawajood.snail.pojo.Cart
import com.tawajood.snail.pojo.CartResponse
import com.tawajood.snail.pojo.OrderBody
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.main.fragments.cart.CartViewModel
import com.tawajood.snail.utils.*
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CheckoutFragment : Fragment(R.layout.fragment_checkout) {


    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var parent: MainActivity
    private val viewModel: CheckoutViewModel by viewModels()
    private var lat: Double? = null
    private var lng: Double? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLatLng: LatLng
    private lateinit var checkoutItemsAdapter: CheckoutItemsAdapter
    private var cartItems = mutableListOf<Cart>()
    private lateinit var cartResponse: CartResponse
    private var payment: String = "0"
    private var total = 0f
    val methods: Array<String> = resources.getStringArray(R.array.Methods)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCheckoutBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        setupUI()
        setupItems()
        observeData()
        onClick()
    }

    private fun setupItems() {
        checkoutItemsAdapter = CheckoutItemsAdapter(object : CheckoutItemsAdapter.OnItemClick {

        })
        binding.rvCheckoutProducts.adapter = checkoutItemsAdapter
    }

    private fun validate(): Boolean {
        if (TextUtils.isEmpty(binding.usernameEt.text)) {
            ToastUtils.showToast(requireContext(), "الاسم مطلوب")
            return false
        }
        if (TextUtils.isEmpty(binding.phoneEt.text)) {
            ToastUtils.showToast(requireContext(), "رقم الجوال مطلوب")

            return false
        }
        if (TextUtils.isEmpty(binding.addressEt.text)) {
            ToastUtils.showToast(requireContext(), "العنوان مطلوب")

            return false
        }

        if (lat == null || lng == null) {
            ToastUtils.showToast(requireContext(), "الرجاء تفعيل GPS للحصول على العنوان")
            return false
        }


        return true
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
        binding.imgBtn.setOnClickListener {
            if (!SmartLocation.with(requireContext()).location().state()
                    .locationServicesEnabled()
            ) {
                ToastUtils.showToast(
                    requireContext(),
                    getString(R.string.location),
                )
            } else {
                parent.showLoading()
                locationPermissionResult.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                Log.d("islam", "onClick: Hi")
            }
        }

        binding.btnCheckout.setOnClickListener {
            if (validate()) {
                if (binding.methodSpinner.selectedItemPosition == 0) {
                    addOrder()
                } else if (binding.methodSpinner.selectedItemPosition == 1) {
                    checkOut()

                }

            }
        }
    }

    private fun addOrder() {

        viewModel.addOrder(
            OrderBody(
                PrefsHelper.getUserId().toString(),
                binding.usernameEt.text.toString(),
                binding.phoneEt.text.toString(),
                binding.ccp.selectedCountryCode.toString(),
                binding.addressEt.text.toString(),
                payment,
                lat!!.toFloat(),
                lng!!.toFloat()
            )
        )


    }


    private fun setupUI() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item, methods
        )
        binding.methodSpinner.adapter = adapter


        parent.showBottomNav(false)
        binding.usernameEt.setText(PrefsHelper.getUsername())
        binding.phoneEt.setText(PrefsHelper.getPhone())
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {

            viewModel.cartFlow.collectLatest {

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

                        checkoutItemsAdapter.cart = cartItems


                        if (PrefsHelper.getLanguage() == "ar") {
                            binding.tvTotal.text = cartResponse.finalTotal + " ريال "
                            binding.tvTotalPrice.text = cartResponse.total + " ريال "
                            binding.tvDeliveryPrice.text = cartResponse.delivery_cost + " ريال "
                            binding.tvTaxPrice.text = cartResponse.tax + " ريال "
                        } else {
                            binding.tvTotal.text = cartResponse.finalTotal + " RS "
                            binding.tvTotalPrice.text = cartResponse.total + " RS "
                            binding.tvDeliveryPrice.text = cartResponse.delivery_cost + " RS "
                            binding.tvTaxPrice.text = cartResponse.tax + " RS "
                        }

                        if (cartResponse.carts.isNotEmpty()) {
                            total = cartResponse.finalTotal.toFloat()

                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.addOrderFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> parent.showLoading()
                    is Resource.Success -> {
                        PrefsHelper.setCartCount(0)
                        parent.viewModel.setCartCount(0)
                        parent.navController.navigate(R.id.successfulOrderFromCartSheetFragment)
                    }
                }
            }
        }
    }

    private fun checkOut() {
        WebViewLocaleHelper(parent).implementWorkaround()
        parent.showLoading()
        val request = MFExecutePaymentRequest(2, total.toDouble())
        MFSDK.executePayment(
            parent,
            request,
            MFAPILanguage.EN,
            onInvoiceCreated = {
                Log.d("islam", "invoiceId: $it")
            }
        ) { _: String, result: MFResult<MFGetPaymentStatusResponse> ->
            parent.hideLoading()
            when (result) {
                is MFResult.Success -> {
                    Log.d("islam", "Response: " + Gson().toJson(result.response))
                    addOrder()
                }
                is MFResult.Fail -> {
                    ToastUtils.showToast(
                        requireContext(),
                        result.error.message.toString()
                    )
                    Log.d("islam", "Fail: " + Gson().toJson(result.error))
                }
                MFResult.Loading -> parent.showLoading()
            }
        }
    }

    private val locationPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        Log.d("islam", "Request Per: Hi")
        if (result) {
            getCurrentLocation()
        } else {
            Log.e("islam", "onActivityResult: PERMISSION DENIED")
            Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getCurrentLocation() {
        parent.showLoading()
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationClient.getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnCompleteListener {
                parent.hideLoading()
                it.addOnSuccessListener { location ->
                    if (location != null) {
                        currentLatLng = LatLng(location.latitude, location.longitude)
                        lat = location.latitude
                        lng = location.longitude

                        getAddressForTextView(
                            requireContext(),
                            location.latitude,
                            location.longitude,
                            binding.addressEt
                        )
                    } else {
                        getCurrentLocation()
                    }
                }
                it.addOnFailureListener { e ->
                    Log.d("islam", "getLastKnownLocation: ${e.message}")
                }
            }
    }

}
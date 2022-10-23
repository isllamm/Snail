package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.adapters.SubCategoriesAdapter
import com.tawajood.snail.adapters.VendorsAdapter
import com.tawajood.snail.databinding.FragmentAnimalStoreBinding
import com.tawajood.snail.databinding.FragmentStoreBinding
import com.tawajood.snail.databinding.FragmentSubCategoriesBinding
import com.tawajood.snail.pojo.Store
import com.tawajood.snail.pojo.SubCategory
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
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
    private lateinit var subCategoriesAdapter: SubCategoriesAdapter
    private var subCats = mutableListOf<SubCategory>()
    private var vendorId by Delegates.notNull<Int>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSubCategoriesBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        vendorId = requireArguments().getInt(Constants.VENDOR_ID)

        setupUI()
        setupSubRec()
        observeData()
        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        binding.tv.text = "الاقسام الفرعية"
        viewModel.getSubCategory(vendorId.toString())
        parent.showBottomNav(false)
    }

    private fun setupSubRec() {
        subCategoriesAdapter = SubCategoriesAdapter(object : SubCategoriesAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                parent.navController.navigate(
                    R.id.productsFragment,
                    bundleOf(Constants.SUB_CAT_ID to subCats[position].id)
                )
            }

        })

        binding.rvSub.adapter = subCategoriesAdapter

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
                        if (subCats.isEmpty() || subCats == null) {
                            binding.message.isVisible = true
                            binding.message.text = it.message.toString()
                        } else {
                            binding.message.isVisible = false
                            subCategoriesAdapter.subCategory = subCats

                        }
                    }
                }
            }
        }
    }


}
package com.tawajood.snail.ui.main.fragments.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentSettingsBinding
import com.tawajood.snail.databinding.FragmentStoreBinding
import com.tawajood.snail.ui.main.MainActivity


class StoreFragment : Fragment(R.layout.fragment_store) {


    private lateinit var binding: FragmentStoreBinding
    private lateinit var parent: MainActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStoreBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()

        onClick()
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


}
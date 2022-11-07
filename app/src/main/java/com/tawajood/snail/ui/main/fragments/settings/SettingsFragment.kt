package com.tawajood.snail.ui.main.fragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.FragmentProductInfoBinding
import com.tawajood.snail.databinding.FragmentSettingsBinding
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.updateLanguage
import dagger.hilt.android.AndroidEntryPoint
import it.beppi.tristatetogglebutton_library.TriStateToggleButton

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {


    private lateinit var binding: FragmentSettingsBinding
    private lateinit var parent: MainActivity
    private var isAr = true
    private var isON = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(requireView())
        parent = requireActivity() as MainActivity
        binding.scLanguageSwitcher.isChecked = PrefsHelper.getLanguage() == Constants.EN



        setupUI()

        onClick()
    }

    private fun onClick() {
        binding.scLanguageSwitcher.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                PrefsHelper.setLanguage(Constants.EN)
            } else {
                PrefsHelper.setLanguage(Constants.AR)
            }
            updateLang()
        }
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }

        binding.arTv.setOnClickListener {
            PrefsHelper.setLanguage(Constants.AR)
            updateLang()
        }

        binding.enTv.setOnClickListener {
            PrefsHelper.setLanguage(Constants.EN)
            updateLang()
        }

    }

    private fun setArClicked() {
        binding.arTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.arTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.brown
        )
        binding.enTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.enTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.light_beige
        )
    }

    private fun setEnClicked() {
        binding.enTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.enTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.brown
        )
        binding.arTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.arTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.light_beige
        )
    }


    private fun setOnArClicked() {
        binding.onTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.onTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.brown
        )
        binding.offTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.offTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.light_beige
        )
    }

    private fun setOffClicked() {
        binding.offTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        binding.offTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.brown
        )
        binding.onTv.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.onTv.backgroundTintList = ContextCompat.getColorStateList(
            requireContext(),
            R.color.light_beige
        )
    }


    private fun setupUI() {
        parent.showBottomNav(false)
    }

    private fun updateLang() {
        updateLanguage(parent.applicationContext)
        parent.finish()
        val intent = Intent(parent, MainActivity::class.java)
        startActivity(intent)
    }
}
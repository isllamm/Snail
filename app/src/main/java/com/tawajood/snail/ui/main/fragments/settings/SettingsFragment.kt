package com.tawajood.snail.ui.main.fragments.settings

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSettingsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()

        onClick()
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }

        binding.btnToggleLang.setOnToggleChanged { toggleStatus, booleanToggleStatus, toggleIntValue ->
            when (toggleStatus) {
                TriStateToggleButton.ToggleStatus.on -> {
                    PrefsHelper.setLanguage(Constants.AR)
                    updateLang()
                }
                TriStateToggleButton.ToggleStatus.mid ->{}
                TriStateToggleButton.ToggleStatus.off -> {
                    PrefsHelper.setLanguage(Constants.AR)
                    updateLang()
                }
            }
        }
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
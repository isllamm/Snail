package com.tawajood.snail.ui.main.fragments.search

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentSearchSheetBinding
import com.tawajood.snail.ui.main.MainActivity

class SearchSheet : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentSearchSheetBinding
    private lateinit var parent: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSheetBinding.inflate(inflater)
        parent = requireActivity() as MainActivity

        onClick()

        return binding.root
    }

    @SuppressLint("CutPasteId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setOnShowListener {
            val bottomSheet =
                bottomSheetDialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior: BottomSheetBehavior<*> =
                BottomSheetBehavior.from(bottomSheet!!)
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    private fun onClick(){
        binding.backImg.setOnClickListener {
            dismiss()
        }
        binding.btn.setOnClickListener {
            parent.navController.navigate(R.id.filterResultFragmentFragment)
        }
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme
}
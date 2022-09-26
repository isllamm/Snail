package com.tawajood.snail.ui.main.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentCancelReservationSheetBinding
import com.tawajood.snail.databinding.FragmentSuccessfulOrderSheetBinding
import com.tawajood.snail.ui.main.MainActivity

class SuccessfulOrderSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentSuccessfulOrderSheetBinding
    private lateinit var parent: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessfulOrderSheetBinding.inflate(inflater)
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
            behavior.skipCollapsed = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return bottomSheetDialog
    }

    private fun onClick() {

        binding.btnOk.setOnClickListener {
            parent.navController.navigate(R.id.paymentFragment)
        }

    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme


}
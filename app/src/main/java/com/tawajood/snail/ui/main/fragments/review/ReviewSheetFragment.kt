package com.tawajood.snail.ui.main.fragments.review

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentReviewSheetBinding
import com.tawajood.snail.ui.dialogs.ResultDialogFragment
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Constants
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlin.properties.Delegates

@AndroidEntryPoint
class ReviewSheetFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentReviewSheetBinding
    private lateinit var parent: MainActivity
    private val viewModel: ReviewViewModel by viewModels()
    private var clinicId by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewSheetBinding.inflate(inflater)
        parent = requireActivity() as MainActivity
        clinicId = requireArguments().getInt(Constants.REVIEW_CLINIC)

        onClick()
        observeData()
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

    private fun onClick() {

        binding.btnSave.setOnClickListener {
            if (binding.rate.rating != 0f)
                viewModel.review(
                    clinicId.toString(),
                    binding.rate.rating.toString(),
                    binding.detailsEt.text.toString(),

                    )
            else
                ToastUtils.showToast(
                    requireContext(),
                    getString(R.string.choose_rate)
                )
        }
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.reviewFlow.collectLatest {
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
                        Log.d("islam", "observeData: $parentFragment")
                        dismiss()
                        ResultDialogFragment.newInstance(
                            getString(R.string.done),
                            R.drawable.done
                        )
                            .show(
                                parentFragmentManager,
                                ResultDialogFragment::class.java.canonicalName
                            )

                    }
                }
            }
        }
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme


}
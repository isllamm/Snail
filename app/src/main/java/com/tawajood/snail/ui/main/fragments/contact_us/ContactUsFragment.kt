package com.tawajood.snail.ui.main.fragments.contact_us

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.tawajood.snail.R
import com.tawajood.snail.databinding.FragmentAddInoculationBinding
import com.tawajood.snail.databinding.FragmentContactUsBinding
import com.tawajood.snail.pojo.Contact
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ContactUsFragment : Fragment(R.layout.fragment_contact_us) {


    private lateinit var binding: FragmentContactUsBinding
    private lateinit var parent: MainActivity
    private lateinit var contact: Contact

    private val viewModel: ContactViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactUsBinding.bind(requireView())
        parent = requireActivity() as MainActivity

        setupUI()
        observeData()
        onClick()
    }

    private fun onClick() {

        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }

        binding.tv3.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, contact.email)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
        }

        binding.t2v3.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, contact.email)
            intent.type = "message/rfc822"
            startActivity(Intent.createChooser(intent, "Select email"))
        }
    }

    private fun setupUI() {
        parent.showBottomNav(false)
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.contactFlow.collectLatest {
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
                        contact = it.data!!
                        binding.tv3.text = contact.email
                        binding.t2v3.text = contact.email
                    }
                }
            }
        }
    }

}
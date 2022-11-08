package com.tawajood.snail.ui.main.fragments.notifications

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
import com.tawajood.snail.adapters.NotificationsAdapter
import com.tawajood.snail.databinding.FragmentNotificationsBinding
import com.tawajood.snail.databinding.FragmentSearchResultBinding
import com.tawajood.snail.pojo.Notification
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.Resource
import com.tawajood.snail.utils.ToastUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var parent: MainActivity
    private lateinit var adapter: NotificationsAdapter
    private var notifications = mutableListOf<Notification>()


    private val viewModel: NotificationsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater)
        parent = requireActivity() as MainActivity

        setupUI()
        onClick()
        setupNotifications()
        observeData()

        return binding.root
    }

    private fun onClick() {
        binding.ivBack.setOnClickListener {
            parent.onBackPressed()
        }
    }

    private fun setupUI() {
        parent.showBottomNav(false)

    }

    private fun setupNotifications() {
        adapter = NotificationsAdapter(object : NotificationsAdapter.OnItemClick {
            override fun onItemClickListener(position: Int) {
                if (notifications[position].specialOrderId != null) {

                } else if (notifications[position].orderId != null) {

                }
            }

        })
        binding.rvNotifications.adapter = adapter
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.notificationsFlow.collectLatest {
                parent.hideLoading()
                when (it) {
                    is Resource.Error -> ToastUtils.showToast(
                        requireContext(),
                        it.message.toString()
                    )
                    is Resource.Idle -> {
                    }
                    is Resource.Loading -> {
                        parent.showLoading()
                    }
                    is Resource.Success -> {
                        notifications = it.data!!
                        if (notifications.isEmpty()){
                            binding.rvNotifications.isVisible = false
                            binding.empty.isVisible = true
                        }else{
                            binding.rvNotifications.isVisible = true
                            binding.empty.isVisible = false

                            adapter.notifications = notifications

                        }
                        //binding.emptyTv.isVisible = notifications.isEmpty()
                    }
                }
            }
        }
    }
}
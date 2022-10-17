package com.tawajood.snail.ui.main

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.ActivityMainBinding
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.utils.LoadingUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    private lateinit var loadingUtil: LoadingUtil
    private lateinit var reciever: Receiver
    private val viewModel: MainViewModel by viewModels()
    private lateinit var header: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtil = LoadingUtil(this)
        reciever = Receiver()
        registerReceiver(reciever, IntentFilter(""))
        header = binding.navView.getHeaderView(0)

        setupUI()
        onClick()
        observeData()
        setupNavController()
    }

    private fun onClick() {
        binding.toolbar.ivMenu.setOnClickListener {
            openSideNav()
        }
        binding.navView.menu.findItem(R.id.logout_menu).setOnMenuItemClickListener {
            logout()
            false
        }

        binding.navView.getHeaderView(0).findViewById<ImageView>(R.id.close)
            .setOnClickListener {
                binding.drawerLayout.closeDrawer(GravityCompat.START)

            }
    }

    fun logout() {
        PrefsHelper.setToken("")
        PrefsHelper.setUserId(-1)
        PrefsHelper.setPhone("")
        PrefsHelper.setUserImage("")
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
    }


    private fun setupUI() {
        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.toolbar.logo)

        binding.navView.itemIconTintList = null

        header.findViewById<TextView>(R.id.username_tv).text = PrefsHelper.getUsername()
        Glide.with(header.context)
            .load(PrefsHelper.getUserImage())
            .into(header.findViewById<ImageView>(R.id.profile_img))
    }

    fun showToolbar(isVisible: Boolean) {
        binding.toolbar.root.isVisible = isVisible
    }

    private fun openSideNav() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    fun showBottomNav(isVisible: Boolean) {
        binding.bottomNavView.isVisible = isVisible
    }

    fun showLoading() {
        loadingUtil.showLoading()
    }

    fun hideLoading() {
        loadingUtil.hideLoading()
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            viewModel.identifiers.collectLatest {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(reciever)
    }

    private inner class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                viewModel.getIdentifiers()
            }
        }
    }
}
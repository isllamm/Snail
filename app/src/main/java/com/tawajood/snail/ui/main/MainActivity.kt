package com.tawajood.snail.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        onClick()
        setupNavController()
    }

    private fun onClick() {
        binding.toolbar.ivMenu.setOnClickListener {
            openSideNav()
        }
    }

    private fun setupNavController(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavView.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
        binding.navView.itemIconTintList = null

    }



    private fun setupUI(){
        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.toolbar.logo)

        binding.navView.itemIconTintList = null

    }

    fun showToolbar(isVisible: Boolean){
        binding.toolbar.root.isVisible = isVisible
    }
    private fun openSideNav() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }
    fun showBottomNav(isVisible: Boolean){
        binding.bottomNavView.isVisible = isVisible
    }
}
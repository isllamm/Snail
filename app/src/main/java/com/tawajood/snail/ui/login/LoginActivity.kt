package com.tawajood.snail.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.ActivityLoginBinding
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.utils.LoadingUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var navController: NavController
    private lateinit var loadingUtil: LoadingUtil


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadingUtil = LoadingUtil(this)

        setupUI()
        setupNavController()
        onClick()
    }

    private fun setupUI() {
        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.toolbar.logo)
    }

    private fun setupNavController() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.login_nav_host_fragment)
                as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun onClick() {
        binding.toolbar.backImg.setOnClickListener {
            onBackPressed()
        }
    }

    fun setToolbarTitle(title: String) {
        binding.toolbar.titleTv.text = title
    }

    fun setToolbarSubtitle(subtitle: String) {
        binding.toolbar.subtitleTv.text = subtitle
    }

    fun showBackImage(isVisible: Boolean) {
        binding.toolbar.backImg.isVisible = isVisible
    }

    fun gotoMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun showLoading() {
        loadingUtil.showLoading()
    }

    fun hideLoading() {
        loadingUtil.hideLoading()
    }

}
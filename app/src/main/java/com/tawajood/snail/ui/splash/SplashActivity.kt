package com.tawajood.snail.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.ActivitySplashBinding
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.onboard.OnboardActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this)
            .load(R.drawable.splash)
            .into(binding.splashBg)
        Glide.with(this)
            .load(R.drawable.logo)
            .into(binding.logo)

        Handler(Looper.myLooper()!!).postDelayed({
            if (PrefsHelper.isFirst()) {
                startActivity(Intent(this, OnboardActivity::class.java))
            } else {
                if (PrefsHelper.getToken().isEmpty()) {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            finish()
        }, 2000)
    }

}

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
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.onboard.OnboardActivity

@SuppressLint("CustomSplashScreen")
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

        //Splash Screen duration
        Handler(Looper.myLooper()!!).postDelayed({
            startActivity(Intent(this@SplashActivity, OnboardActivity::class.java))
//            if (PrefsHelper.getToken().isNotEmpty()) {
//                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
//            } else {
//                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//            }
            finish()
        }, 2000)
    }

}

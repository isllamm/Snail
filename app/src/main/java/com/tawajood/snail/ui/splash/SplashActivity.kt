package com.tawajood.snail.ui.splash

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.tawajood.snail.R
import com.tawajood.snail.data.PrefsHelper
import com.tawajood.snail.databinding.ActivitySplashBinding
import com.tawajood.snail.ui.login.LoginActivity
import com.tawajood.snail.ui.main.MainActivity
import com.tawajood.snail.ui.onboard.OnboardActivity
import com.tawajood.snail.utils.ToastUtils
import com.tawajood.snail.utils.getAddressForTextView
import dagger.hilt.android.AndroidEntryPoint
import io.nlopez.smartlocation.SmartLocation

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private var lat: Double? = null
    private var lng: Double? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLatLng: LatLng

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

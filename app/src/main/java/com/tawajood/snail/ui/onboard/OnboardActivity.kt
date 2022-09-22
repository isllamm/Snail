package com.tawajood.snail.ui.onboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.tawajood.snail.adapters.SectionsPagerAdapter
import com.tawajood.snail.databinding.ActivityOnboardBinding
import com.tawajood.snail.ui.login.LoginActivity

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding
    private var pos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        onClick()
    }

    private fun setupViewPager(){
        val adapter = SectionsPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager){ _, _ ->
            binding.viewPager.currentItem = 0
        }.attach()
    }

    private fun onClick(){
        binding.nextImg.setOnClickListener {
            goNext()
        }
    }

    private fun goNext(){
        if (pos == 0) {
            pos++
            binding.viewPager.currentItem = binding.viewPager.currentItem + 1
        } else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
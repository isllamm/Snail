package com.tawajood.snail.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tawajood.snail.ui.onboard.fragments.FirstFragment
import com.tawajood.snail.ui.onboard.fragments.SecondFragment

class SectionsPagerAdapter(fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0-> return FirstFragment()
            1-> return SecondFragment()
        }

        return FirstFragment()
    }
}
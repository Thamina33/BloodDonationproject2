package com.rahat.gtaf.blooddonationproject2.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rahat.gtaf.blooddonationproject2.fragment.BloodRequestListFragment
import com.rahat.gtaf.blooddonationproject2.fragment.HomeFragment
import com.rahat.gtaf.blooddonationproject2.fragment.ProfileFragment


private const val NUM_TABS = 3

class ViewPagerAdapter(mainFragment: HomeFragment) :
    FragmentStateAdapter(mainFragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BloodRequestListFragment()
            1 -> return ProfileFragment()
        }
        return BloodRequestListFragment()
    }
}
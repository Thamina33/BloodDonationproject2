package com.the_mystic.diu.blooddonationproject2.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.the_mystic.diu.blooddonationproject2.fragment.BloodRequestListFragment
import com.the_mystic.diu.blooddonationproject2.fragment.HomeFragment
import com.the_mystic.diu.blooddonationproject2.fragment.ProfileFragment
import com.the_mystic.diu.blooddonationproject2.fragment.UserListFragment


private const val NUM_TABS = 3

class ViewPagerAdapter(mainFragment: HomeFragment) :
    FragmentStateAdapter(mainFragment) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return BloodRequestListFragment()
            1 -> return UserListFragment()
            2 -> return  ProfileFragment()
        }
        return BloodRequestListFragment( )
    }
}
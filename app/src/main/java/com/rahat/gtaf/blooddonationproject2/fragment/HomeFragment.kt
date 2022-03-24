package com.rahat.gtaf.blooddonationproject2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.adapter.ViewPagerAdapter
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val navigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_fragment -> {
                    binding.addBtn.visibility = View.VISIBLE
                    binding.viewpager.setCurrentItem(0, false)
                }
                R.id.search_fragment -> {
                    binding.addBtn.visibility = View.GONE
                    binding.viewpager.setCurrentItem(1, false)
                }
                R.id.profile_fragment -> {
                    binding.addBtn.visibility = View.GONE
                    val firebaseUser = FirebaseAuth.getInstance().currentUser
                    if (firebaseUser != null) {
                        binding.viewpager.setCurrentItem(2, false)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "You Are Not Logged In",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            true
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(this)
        binding.viewpager.adapter = adapter

        binding.addBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_blood_requestFragment)
        }

        binding.viewpager.isUserInputEnabled = false
        binding.bottomNav.setOnNavigationItemSelectedListener(navigationItemSelectedListener)
        binding.viewpager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        binding.addBtn.visibility = View.VISIBLE
                        binding.bottomNav.menu.findItem(R.id.home_fragment).isChecked = true
                    }
                    1 -> {
                        binding.addBtn.visibility = View.GONE
                        binding.bottomNav.menu.findItem(R.id.search_fragment).isChecked = true
                    }
                    2 -> {
                        binding.addBtn.visibility = View.GONE
                        val firebaseUser = FirebaseAuth.getInstance().currentUser
                        if (firebaseUser != null) {
                            binding.bottomNav.menu.findItem(R.id.profile_fragment).isChecked = true
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "You Are Not Logged In",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
    }


}
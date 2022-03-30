package com.the_mystic.diu.blooddonationproject2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.the_mystic.diu.blooddonationproject2.R
import com.the_mystic.diu.blooddonationproject2.databinding.ActivityMainBinding
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentNewHomeBinding


class NewHomeFragment : Fragment() {

    private lateinit var binding: FragmentNewHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.feeed.setOnClickListener {
            findNavController().navigate(R.id.action_newHomeFragment_to_homeFragment)
        }
        binding.donor.setOnClickListener {
            findNavController().navigate(R.id.action_newHomeFragment_to_userListFragment)
        }
        binding.request.setOnClickListener {
            findNavController().navigate(R.id.action_newHomeFragment_to_blood_requestFragment)
        }

        binding.hospital.setOnClickListener {
            findNavController().navigate(R.id.action_newHomeFragment_to_hospitalFragment)
        }

        binding.org.setOnClickListener {
            findNavController().navigate(R.id.action_newHomeFragment_to_orgFragment)
        }

        binding.diease.setOnClickListener {
            findNavController().navigate(R.id.action_newHomeFragment_to_dieaseFragment)
        }

    }
}
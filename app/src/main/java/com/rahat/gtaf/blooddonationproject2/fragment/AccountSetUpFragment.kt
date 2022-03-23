package com.rahat.gtaf.blooddonationproject2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentAccountSetUpBinding


class AccountSetUpFragment : Fragment() {
    lateinit var binding: FragmentAccountSetUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountSetUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
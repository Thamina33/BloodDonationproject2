package com.rahat.gtaf.blooddonationproject2.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.rahat.gtaf.blooddonationproject2.R


class SplashScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val user = FirebaseAuth.getInstance().currentUser



        Handler(Looper.getMainLooper()).postDelayed({

            if (user != null) {
                findNavController().navigate(R.id.action_splashScreenFragment_to_homeFragment)
            } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_signInFragment)
            }

        }, 800)
    }
}
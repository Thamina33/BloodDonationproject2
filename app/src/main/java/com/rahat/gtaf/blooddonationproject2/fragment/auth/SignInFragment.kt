package com.rahat.gtaf.blooddonationproject2.fragment.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding
    lateinit var ctx: Context
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        ctx = binding.root.context
        mAuth = FirebaseAuth.getInstance()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signIn.setOnClickListener() {
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()

            if (email.isNotEmpty() || pass.isNotEmpty()) {
                logInUser(email, pass)
            } else Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show()



        }

        binding.signup.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }

    }

    private fun logInUser(email: String, pass: String) {
     mAuth.signInWithEmailAndPassword(email,pass).addOnSuccessListener {

        findNavController().navigate(R.id.action_signInFragment_to_homeFragment)

     }
         .addOnFailureListener(){
         Toast.makeText(ctx, "Error ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
     }
    }
}

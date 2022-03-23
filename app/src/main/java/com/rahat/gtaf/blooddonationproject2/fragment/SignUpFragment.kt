package com.rahat.gtaf.blooddonationproject2.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment() {

    lateinit var binding: FragmentSignUpBinding
    lateinit var ctx: Context
    lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        ctx = binding.root.context
        mAuth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signup.setOnClickListener {

            val name = binding.name.text.toString()
            val email = binding.email.text.toString()
            val pass = binding.pass.text.toString()

            if (name.isNotEmpty() || email.isNotEmpty() || pass.isNotEmpty()) {
                RegisterUser(name, email, pass)
            } else Toast.makeText(ctx, "Error", Toast.LENGTH_SHORT).show()

        }

        binding.signin.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun RegisterUser(name: String, email: String, pass: String) {

        mAuth.createUserWithEmailAndPassword(
            email, pass
        ).addOnSuccessListener {

        }.addOnFailureListener {
            Toast.makeText(ctx, "Error ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
        }


    }

    private fun CreateUserDetail() {
        //
        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
    }
}
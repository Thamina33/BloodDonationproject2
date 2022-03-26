package com.rahat.gtaf.blooddonationproject2.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rahat.gtaf.blooddonationproject2.Const
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentAccountSetUpBinding
import com.rahat.gtaf.blooddonationproject2.toast


class AccountSetUpFragment : Fragment() {
    private lateinit var mauth: FirebaseAuth
    lateinit var binding: FragmentAccountSetUpBinding
    var bg: String = ""
    val list_of_items = arrayListOf<String>("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mauth = FirebaseAuth.getInstance()
        binding = FragmentAccountSetUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadBloodGroup()

       binding.mail.text =  arguments?.getString("mail").toString()
       binding.name.text = arguments?.getString("name").toString()

        binding.submitButton.setOnClickListener {
            val phone = binding.ph.text.toString()
            val address = binding.address.text.toString()
            val isDonate = binding.checkbox.isSelected
            val uid = mauth.uid.toString()
            val hashMap: HashMap<String, Any> = HashMap()
            hashMap["phone"] = phone
            hashMap["address"] = address
            hashMap["lat"] = 0.0
            hashMap["lon"] = 0.0
            hashMap["isDonate"] = isDonate
            hashMap["bg"] = bg
            hashMap["uid"] = uid
            hashMap["mail"] = arguments?.getString("mail").toString()
            hashMap["name"] = arguments?.getString("name").toString()
            val ref = FirebaseDatabase.getInstance().getReference(Const.user_path).child(uid);

            ref.updateChildren(hashMap).addOnFailureListener {
                "Error ${it.message}".toast(requireContext())
            }.addOnSuccessListener {
                "User Updated".toast(requireContext())
                findNavController().navigate(R.id.action_accountSetUpFragment_to_homeFragment)
            }

        }


    }

    private fun loadBloodGroup() {
        val aa = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, list_of_items)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.bloodGrp.adapter = aa

        binding.bloodGrp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                bg = list_of_items[position].toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }
}
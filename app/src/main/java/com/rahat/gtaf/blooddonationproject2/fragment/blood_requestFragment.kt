package com.rahat.gtaf.blooddonationproject2.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rahat.gtaf.blooddonationproject2.*
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentBloodRequestBinding
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentSignInBinding
import kotlinx.coroutines.flow.combineTransform


class blood_requestFragment : Fragment() {
    lateinit var binding: FragmentBloodRequestBinding
    lateinit var ctx: Context
    var blood_grp = ""
    val list_of_items = arrayListOf<String>("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBloodRequestBinding.inflate(inflater, container, false)
        ctx = binding.root.context
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(ctx, android.R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        binding.bloodGrp.adapter = aa

        binding.bloodGrp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                blood_grp = list_of_items[position].toString()

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        binding.subitBtn.setOnClickListener {
            val phone = binding.ph.text.toString()
            val hosName = binding.hospitalnName.text.toString()
            val loc = binding.location.text.toString()
            val hashMap: HashMap<String, Any> = HashMap()
            if (phone.isNotEmpty() || hosName.isNotEmpty() || loc.isNotEmpty()) {
                hashMap["phone"] = phone
                hashMap["hosName"] = hosName
                hashMap["loc"] = loc
                hashMap["blood"] = blood_grp
                hashMap["req_uid"] = FirebaseAuth.getInstance().uid.toString()
                hashMap["req_time"] = getCurrentDate()

                val ref = FirebaseDatabase.getInstance().getReference(Const.req_db_path)

                val key = ref.push().key.toString()
                hashMap["id"] = key
                val dialog = HelperClass.createProgressDialog(binding.root.context, "Loading...")
                dialog.show()

                ref.child(key).setValue(hashMap).addOnFailureListener {
                    dialog.dismiss()
                    "Error ${it.message}".toast(requireContext())
                }.addOnSuccessListener {
                    dialog.dismiss()
                    "Request Added".toast(requireContext())
                    findNavController().popBackStack()
                }
            } else {
                "Insert Every Value".toast(requireContext())
            }

        }


    }
}
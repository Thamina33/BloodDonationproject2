package com.rahat.gtaf.blooddonationproject2.fragment

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.Target
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.rahat.gtaf.blooddonationproject2.Const
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentProfileBinding
import com.rahat.gtaf.blooddonationproject2.model.ProfileModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = FirebaseAuth.getInstance().uid.toString()
        loadProfileData(uid)
    }

    private fun loadProfileData(uid: String) {
        var context: Context
        val dialog = ProgressDialog(activity)
        dialog.setMessage("Loading Profile Data")
        dialog.setCancelable(false)
        dialog.show()
        val mref = FirebaseDatabase.getInstance().reference

        mref.child(Const.user_path).child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val model = snapshot.getValue(ProfileModel::class.java)
                        binding.proName.setText(model?.name)
                        binding.mail.setText(model?.mail)
                        binding.phone.setText(model?.phone)
                        binding.address.setText(model?.address)
//                    try {
//                        val downloadURL: String = model.getPp()
//                        Glide.with(getContext()!!)
//                            .load(downloadURL)
//                            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                            .error(R.drawable.traveller)
//                            .into<Target<Drawable>>(binding.profilePic)
//                    } catch (e: Exception) {
//                    }
                        dialog.dismiss()
                    } else {
                        dialog.dismiss()
                        Toast.makeText(
                            getContext(),
                            "Error : Profile Not Found!!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    dialog.dismiss()
                    Toast.makeText(getContext(), "Error : " + error.message, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}
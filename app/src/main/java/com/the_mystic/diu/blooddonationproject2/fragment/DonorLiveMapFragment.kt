package com.the_mystic.diu.blooddonationproject2.fragment

import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.the_mystic.diu.blooddonationproject2.Const
import com.the_mystic.diu.blooddonationproject2.R
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentDonorLiveMapBinding
import com.the_mystic.diu.blooddonationproject2.model.ProfileModel
import java.lang.Exception


class DonorLiveMapFragment : Fragment(), GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    OnMapReadyCallback {
    var latLng: LatLng? = null
    private var userlist: MutableList<ProfileModel> = mutableListOf()
    private var map: GoogleMap? = null
    private lateinit var binding: FragmentDonorLiveMapBinding
    private lateinit var mAuth: FirebaseAuth
    var uid: String = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance()
        binding = FragmentDonorLiveMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        uid = mAuth.uid.toString()
        loadMap()

        loadAllUser()

    }

    private fun loadMap() {

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMyLocationButtonClick(): Boolean {

        return false
    }

    override fun onMyLocationClick(p0: Location) {

    }

    override fun onMapReady(p0: GoogleMap) {
        map = p0

    }


    private fun loadAllUser() {
        userlist.clear()
        val mref = FirebaseDatabase.getInstance().reference

        mref.child(Const.user_path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userlist.clear()
                map?.clear()
                for (ds in dataSnapshot.children) {
                    val eventModel: ProfileModel? = ds.getValue(ProfileModel::class.java)

                    if (eventModel != null) {
                        userlist.add(eventModel)
                    }

                    try {
                        if (eventModel?.uid == uid) {
                            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    eventModel.lat,
                                    eventModel.lon
                                ), 12f
                            )
                            map?.animateCamera(cameraUpdate)
                        }
                        eventModel?.lat?.let { LatLng(it, eventModel.lon) }
                            ?.let {
                                MarkerOptions().position(it)
                                    .title("${eventModel.name} (${eventModel.bg}) ${eventModel.phone}")
                            }
                            ?.let { map?.addMarker(it) }


                    } catch (Ex: Exception) {

                    }


                }


            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        map = null

    }
}
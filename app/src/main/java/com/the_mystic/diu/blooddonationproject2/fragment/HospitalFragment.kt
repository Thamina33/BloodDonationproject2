package com.the_mystic.diu.blooddonationproject2.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.the_mystic.diu.blooddonationproject2.Const
import com.the_mystic.diu.blooddonationproject2.R
import com.the_mystic.diu.blooddonationproject2.adapter.HospitalModelListAdapter
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentHospitalBinding
import com.the_mystic.diu.blooddonationproject2.model.HospitalModel
import com.the_mystic.diu.blooddonationproject2.model.ProfileModel


class HospitalFragment : Fragment(), HospitalModelListAdapter.Interaction {

    private lateinit var binding: FragmentHospitalBinding
    private lateinit var mAdapter: HospitalModelListAdapter
    private var hospitalList: MutableList<HospitalModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHospitalBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter = HospitalModelListAdapter()


        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }


        loadedEvents()
    }

    private fun loadedEvents() {
        hospitalList.clear()

        val mref = FirebaseDatabase.getInstance().reference

        mref.child(Const.hospital).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hospitalList.clear()
                for (ds in dataSnapshot.children) {
                    val eventModel: HospitalModel? = ds.getValue(HospitalModel::class.java)

                    if (eventModel != null) {
                        hospitalList.add(eventModel)
                    }

                }

                mAdapter.submitList(hospitalList)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onItemSelected(position: Int, item: HospitalModel) {

    }
}
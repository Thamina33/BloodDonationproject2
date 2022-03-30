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
import com.the_mystic.diu.blooddonationproject2.adapter.DieseaseListAdapter
import com.the_mystic.diu.blooddonationproject2.adapter.OrgListAdapter
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentDieaseBinding
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentOrgBinding
import com.the_mystic.diu.blooddonationproject2.model.DieseaseModel
import com.the_mystic.diu.blooddonationproject2.model.OrgModel


class DieaseFragment : Fragment() , DieseaseListAdapter.Interaction {


    private lateinit var binding: FragmentDieaseBinding
    private lateinit var mAdapter: DieseaseListAdapter
    private var hospitalList: MutableList<DieseaseModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDieaseBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = DieseaseListAdapter()

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        loadedEvents()
    }


    private fun loadedEvents() {
        hospitalList.clear()

        val mref = FirebaseDatabase.getInstance().reference

        mref.child("disease").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hospitalList.clear()
                for (ds in dataSnapshot.children) {
                    val eventModel: DieseaseModel? = ds.getValue(DieseaseModel::class.java)

                    if (eventModel != null) {
                        hospitalList.add(eventModel)
                    }

                }

                mAdapter.submitList(hospitalList)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onItemSelected(position: Int, item: DieseaseModel) {

    }
}
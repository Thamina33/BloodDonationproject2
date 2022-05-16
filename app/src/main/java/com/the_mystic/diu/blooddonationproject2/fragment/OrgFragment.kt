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
import com.the_mystic.diu.blooddonationproject2.adapter.OrgListAdapter
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentOrgBinding
import com.the_mystic.diu.blooddonationproject2.model.OrgModel


class OrgFragment : Fragment() {

    private lateinit var binding: FragmentOrgBinding
    private lateinit var mAdapter: OrgListAdapter
    private var hospitalList: MutableList<OrgModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment.
        binding = FragmentOrgBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAdapter = OrgListAdapter()

        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter
        }

        loadedEvents()

    }

    private fun loadedEvents() {
        hospitalList.clear()

        val mref = FirebaseDatabase.getInstance().reference

        mref.child(Const.org).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                hospitalList.clear()
                for (ds in dataSnapshot.children) {
                    val eventModel: OrgModel? = ds.getValue(OrgModel::class.java)

                    if (eventModel != null) {
                        hospitalList.add(eventModel)
                    }

                }

                mAdapter.submitList(hospitalList)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
}
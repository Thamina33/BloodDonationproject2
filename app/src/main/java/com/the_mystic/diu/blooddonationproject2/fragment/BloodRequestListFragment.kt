package com.the_mystic.diu.blooddonationproject2.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.the_mystic.diu.blooddonationproject2.Const
import com.the_mystic.diu.blooddonationproject2.adapter.ReqListAdapter
import com.the_mystic.diu.blooddonationproject2.databinding.FragmentBloodRequestListBinding
import com.the_mystic.diu.blooddonationproject2.model.ReqModel


class BloodRequestListFragment : Fragment(), ReqListAdapter.Interaction {
    private lateinit var mAdapter: ReqListAdapter
    private lateinit var binding: FragmentBloodRequestListBinding
    private val reqList: MutableList<ReqModel> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBloodRequestListBinding.inflate(inflater, container, false)
        mAdapter = ReqListAdapter(this)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.list.apply {
            layoutManager = LinearLayoutManager(binding.root.context)
            adapter = mAdapter
        }

    }


    private fun loadedEvents() {
        reqList.clear()
        val mref = FirebaseDatabase.getInstance().reference

        mref.child(Const.req_db_path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                reqList.clear()
                for (ds in dataSnapshot.children) {
                    val eventModel: ReqModel? = ds.getValue(ReqModel::class.java)

                    if (eventModel != null) {
                        reqList.add(eventModel)
                    }

                }

                mAdapter.submitList(reqList)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onItemSelected(position: Int, item: ReqModel) {

    }

    override fun onResume() {
        super.onResume()
        loadedEvents()
    }
}
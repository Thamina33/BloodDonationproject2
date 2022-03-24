package com.rahat.gtaf.blooddonationproject2.fragment

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
import com.rahat.gtaf.blooddonationproject2.Const
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.adapter.UserListAdapter
import com.rahat.gtaf.blooddonationproject2.databinding.FragmentUserListBinding
import com.rahat.gtaf.blooddonationproject2.databinding.RowForHelpPostBinding
import com.rahat.gtaf.blooddonationproject2.model.ProfileModel
import com.rahat.gtaf.blooddonationproject2.model.ReqModel


class UserListFragment : Fragment(), UserListAdapter.Interaction {

    private lateinit var binding: FragmentUserListBinding
    private lateinit var mAdapter: UserListAdapter
    private var userlist: MutableList<ProfileModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mAdapter = UserListAdapter(this)
        binding = FragmentUserListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(binding.root.context)
        }

        loadedEvents()

    }

    private fun loadedEvents() {
        userlist.clear()
        val mref = FirebaseDatabase.getInstance().reference

        mref.child(Const.user_path).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userlist.clear()
                for (ds in dataSnapshot.children) {
                    val eventModel: ProfileModel? = ds.getValue(ProfileModel::class.java)

                    if (eventModel != null) {
                        userlist.add(eventModel)
                    }

                }

                mAdapter.submitList(userlist)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onItemSelected(position: Int, item: ProfileModel) {

    }
}
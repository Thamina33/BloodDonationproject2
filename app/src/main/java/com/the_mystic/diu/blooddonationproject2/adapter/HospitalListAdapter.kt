package com.the_mystic.diu.blooddonationproject2.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.the_mystic.diu.blooddonationproject2.R
import com.the_mystic.diu.blooddonationproject2.databinding.ItemHospitalBinding
import com.the_mystic.diu.blooddonationproject2.databinding.RowForHelpPostBinding
import com.the_mystic.diu.blooddonationproject2.model.HospitalModel

class HospitalModelListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HospitalModel>() {

        override fun areItemsTheSame(oldItem: HospitalModel, newItem: HospitalModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HospitalModel, newItem: HospitalModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ReqViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_hospital,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReqViewholder -> {
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<HospitalModel>) {
        differ.submitList(list)
    }

    class ReqViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemHospitalBinding.bind(itemView)

        fun bind(item: HospitalModel) {
            itemView.setOnClickListener {
               // interaction?.onItemSelected(adapterPosition, item)
            }

            binding.nameTv.text = item.name
            binding.descTv.text = item.address
            binding.phTv.text = item.phone

            Glide.with(binding.root.context)
                .load(item.image)
                .into(binding.image)


        }


    }

    interface Interaction {
        fun onItemSelected(position: Int, item: HospitalModel)
    }
}

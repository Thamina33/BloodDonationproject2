package com.rahat.gtaf.blooddonationproject2.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.rahat.gtaf.blooddonationproject2.R
import com.rahat.gtaf.blooddonationproject2.databinding.RowForHelpPostBinding
import com.rahat.gtaf.blooddonationproject2.model.ReqModel

class ReqListAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReqModel>() {

        override fun areItemsTheSame(oldItem: ReqModel, newItem: ReqModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReqModel, newItem: ReqModel): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return ReqViewholder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_for_help_post,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ReqViewholder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<ReqModel>) {
        differ.submitList(list)
    }

    class ReqViewholder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {
        val binding = RowForHelpPostBinding.bind(itemView)

        fun bind(item: ReqModel) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }

            binding.bloodGrp.text = item.blood
            binding.location.text = item.loc
            binding.text3.text = item.req_time
            binding.name.text = item.hosName

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: ReqModel)
    }
}

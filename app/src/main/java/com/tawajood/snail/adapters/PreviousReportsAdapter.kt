package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemMyAnimalBinding
import com.tawajood.snail.databinding.ItemPreviousReportsBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.pojo.Pet

class PreviousReportsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<PreviousReportsAdapter.PreviousReportsViewHolder>() {
    var consultant = mutableListOf<Consultant>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class PreviousReportsViewHolder(val binding: ItemPreviousReportsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PreviousReportsAdapter.PreviousReportsViewHolder {
        val binding =
            ItemPreviousReportsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PreviousReportsAdapter.PreviousReportsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PreviousReportsAdapter.PreviousReportsViewHolder,
        position: Int
    ) {
        holder.binding.tvDate.text = consultant[position].created_at.substring(0, 10)
        holder.binding.tvNameClinic.text = consultant[position].clinic.name
        holder.binding.typeEt.text = consultant[position].specialization
        holder.binding.descriptionEt.text = consultant[position].clinic_report

        Glide.with(holder.itemView.context).load(consultant[position].clinic.image_clinic)
            .into(holder.binding.clicnicImg)

        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return consultant.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
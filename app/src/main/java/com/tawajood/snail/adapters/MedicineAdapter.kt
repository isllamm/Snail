package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemDescriptionBinding
import com.tawajood.snail.databinding.ItemPreviousReportsBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.pojo.Medicine

class MedicineAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {
    var consultant = mutableListOf<Consultant>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MedicineViewHolder(val binding: ItemDescriptionBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MedicineAdapter.MedicineViewHolder {
        val binding =
            ItemDescriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MedicineAdapter.MedicineViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MedicineAdapter.MedicineViewHolder,
        position: Int
    ) {
        holder.binding.tvDate.text = consultant[position].created_at.substring(0, 10)
        holder.binding.tvNameClinic.text = consultant[position].clinic.name
        holder.binding.descriptionEt.text = consultant[position].medicines[position].medicine

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
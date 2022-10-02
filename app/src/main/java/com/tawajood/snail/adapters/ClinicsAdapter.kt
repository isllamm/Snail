package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemClinicBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.utils.OnItemClickListener

class ClinicsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ClinicsAdapter.ClinicViewHolder>() {

    var clinics = mutableListOf<Clinic>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ClinicViewHolder(val binding: ItemClinicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        val binding = ItemClinicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        holder.binding.titleTv.text = clinics[position].name
        holder.binding.rating.rating = clinics[position].rating
        Glide.with(holder.itemView)
            .load(clinics[position].img)
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return clinics.size
    }
    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }

}
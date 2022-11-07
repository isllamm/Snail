package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemPreviousReportsBinding
import com.tawajood.snail.databinding.ItemVaccinationBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.pojo.PetVaccinations

class VaccinationsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<VaccinationsAdapter.VaccinationsViewHolder>() {
    var petVaccinations = mutableListOf<PetVaccinations>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class VaccinationsViewHolder(val binding: ItemVaccinationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VaccinationsAdapter.VaccinationsViewHolder {
        val binding =
            ItemVaccinationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VaccinationsAdapter.VaccinationsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VaccinationsAdapter.VaccinationsViewHolder,
        position: Int
    ) {
        holder.binding.tvDate.text = petVaccinations[position].date
        holder.binding.tvName.text = petVaccinations[position].vaccinationType.name


        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return petVaccinations.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
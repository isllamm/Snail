package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.snail.databinding.ItemSpecializationBinding
import com.tawajood.snail.pojo.Specializations

class SpecializationAdapter :
    RecyclerView.Adapter<SpecializationAdapter.SpecializationViewHolder>() {

    var specialization = mutableListOf<Specializations>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class SpecializationViewHolder(val binding: ItemSpecializationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpecializationAdapter.SpecializationViewHolder {
        val binding =
            ItemSpecializationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SpecializationAdapter.SpecializationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SpecializationAdapter.SpecializationViewHolder,
        position: Int
    ) {
        holder.binding.name.text = specialization[position].specialization.name

    }

    override fun getItemCount(): Int {
        return specialization.size
    }

}
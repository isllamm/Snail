package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemAnimalBinding
import com.tawajood.snail.pojo.Pet

class RecordedPetsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<RecordedPetsAdapter.RecordedPetsViewHolder>() {
    var pets = mutableListOf<Pet>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int) {
        pets.forEach {
            it.isSelected = false
        }
        pets[position].isSelected = true
        notifyDataSetChanged()
    }

    class RecordedPetsViewHolder(val binding: ItemAnimalBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordedPetsAdapter.RecordedPetsViewHolder {
        val binding =
            ItemAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecordedPetsAdapter.RecordedPetsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecordedPetsAdapter.RecordedPetsViewHolder,
        position: Int
    ) {

        holder.binding.tvName.text = pets[position].name
        Glide.with(holder.itemView)
            .load(pets[position].image)
            .into(holder.binding.ivAnimal)
        if (pets[position].isSelected) {

        } else {

        }

        holder.itemView.setOnClickListener {
            setSelected(position)
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return pets.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
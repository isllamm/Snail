package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemMyAnimalBinding
import com.tawajood.snail.pojo.Pet

class MyPetsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<MyPetsAdapter.MyPetsViewHolder>() {
    var pets = mutableListOf<Pet>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MyPetsViewHolder(val binding: ItemMyAnimalBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyPetsAdapter.MyPetsViewHolder {
        val binding =
            ItemMyAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyPetsAdapter.MyPetsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyPetsAdapter.MyPetsViewHolder, position: Int) {

        holder.binding.tvName.text = pets[position].name
        holder.binding.tvTyp.text = pets[position].type
        Glide.with(holder.itemView)
            .load(pets[position].image)
            .into(holder.binding.ivAnimal)

        holder.itemView.setOnClickListener {
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
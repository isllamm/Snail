package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemCategoryBinding
import com.tawajood.snail.databinding.ItemDayBinding
import com.tawajood.snail.pojo.Categories
import com.tawajood.snail.pojo.Day

class CategoriesAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {
    var cats = mutableListOf<Categories>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class CategoriesViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoriesAdapter.CategoriesViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesAdapter.CategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoriesAdapter.CategoriesViewHolder, position: Int) {

        holder.binding.name.text = cats[position].name
        Glide.with(holder.itemView.context).load(cats[position].image)
            .into(holder.binding.imageView10)


        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return cats.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.snail.databinding.ItemSubcategoryBinding
import com.tawajood.snail.databinding.ItemTimeBinding
import com.tawajood.snail.pojo.SubCategory
import com.tawajood.snail.pojo.Times

class SubCategoriesAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<SubCategoriesAdapter.SubCategoriesViewHolder>() {
    var subCategory = mutableListOf<SubCategory>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class SubCategoriesViewHolder(val binding: ItemSubcategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SubCategoriesAdapter.SubCategoriesViewHolder {
        val binding =
            ItemSubcategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubCategoriesAdapter.SubCategoriesViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SubCategoriesAdapter.SubCategoriesViewHolder,
        position: Int
    ) {

        holder.binding.name.text = subCategory[position].name

        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return subCategory.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
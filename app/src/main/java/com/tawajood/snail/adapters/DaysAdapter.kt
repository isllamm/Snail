package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.databinding.ItemDayBinding
import com.tawajood.snail.pojo.Day

class DaysAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<DaysAdapter.DaysViewHolder>() {
    var days = mutableListOf<Day>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int) {
        days.forEach {
            it.isSelected = false
        }
        days[position].isSelected = true
        notifyDataSetChanged()
    }

    class DaysViewHolder(val binding: ItemDayBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DaysAdapter.DaysViewHolder {
        val binding =
            ItemDayBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DaysAdapter.DaysViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DaysAdapter.DaysViewHolder, position: Int) {

        holder.binding.tvDay.text = days[position].name
        if (days[position].isSelected) {
            holder.binding.container.setBackgroundColor(Color.parseColor("#635238"))
            holder.binding.tvDay.setTextColor(Color.parseColor("#ffffff"))
        } else {
            holder.binding.container.setBackgroundColor(Color.parseColor("#FFF2E3"))
            holder.binding.tvDay.setTextColor(Color.parseColor("#635238"))
        }

        holder.itemView.setOnClickListener {
            setSelected(position)
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return days.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.snail.databinding.ItemDayBinding
import com.tawajood.snail.databinding.ItemTimeBinding
import com.tawajood.snail.pojo.Day
import com.tawajood.snail.pojo.Times

class TimesAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<TimesAdapter.TimesViewHolder>() {
    var times = mutableListOf<Times>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    @SuppressLint("NotifyDataSetChanged")
    private fun setSelected(position: Int) {
        times.forEach {
            it.isSelected = false
        }
        times[position].isSelected = true
        notifyDataSetChanged()
    }

    class TimesViewHolder(val binding: ItemTimeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TimesAdapter.TimesViewHolder {
        val binding =
            ItemTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TimesAdapter.TimesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TimesAdapter.TimesViewHolder, position: Int) {

        holder.binding.tvFrom.text = "from ${times[position].from}"
        holder.binding.tvTo.text = "to ${times[position].to}"

        if (times[position].isSelected) {
            holder.binding.container.setBackgroundColor(Color.parseColor("#635238"))
            holder.binding.tvFrom.setTextColor(Color.parseColor("#ffffff"))
            holder.binding.tvTo.setTextColor(Color.parseColor("#ffffff"))
        } else {
            holder.binding.container.setBackgroundColor(Color.parseColor("#FFF2E3"))
            holder.binding.tvFrom.setTextColor(Color.parseColor("#635238"))
            holder.binding.tvTo.setTextColor(Color.parseColor("#635238"))

        }

        holder.itemView.setOnClickListener {
            setSelected(position)
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return times.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
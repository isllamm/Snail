package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.tawajood.snail.databinding.ItemNewOrderBinding
import com.tawajood.snail.pojo.Order

class OrdersAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {
    var orders = mutableListOf<Order>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class OrdersViewHolder(val binding: ItemNewOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrdersAdapter.OrdersViewHolder {
        val binding =
            ItemNewOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersAdapter.OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.OrdersViewHolder, position: Int) {

        holder.binding.orderDate.text = orders[position].created_at
        holder.binding.tvStatus.text = orders[position].statusName
        holder.binding.tvId.text = orders[position].id.toString()

        when (orders[position].status) {
            0 -> {
                holder.binding.container.setBackgroundColor(Color.parseColor("#F4CA89"))
            }
            1 -> {
                holder.binding.container.setBackgroundColor(Color.parseColor("#F4CA89"))

            }
            2 -> {
                holder.binding.container.setBackgroundColor(Color.parseColor("#F4CA89"))
            }
            3 -> {
                holder.binding.container.setBackgroundColor(Color.parseColor("#635238"))
                holder.binding.tvId.setTextColor(Color.parseColor("#F4CA89"))
                holder.binding.tvStatus.setTextColor(Color.parseColor("#F4CA89"))

            }
        }
        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
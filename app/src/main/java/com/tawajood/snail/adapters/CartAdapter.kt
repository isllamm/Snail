package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemCartBinding
import com.tawajood.snail.databinding.ItemCategoryBinding
import com.tawajood.snail.pojo.Cart
import com.tawajood.snail.pojo.Categories

class CartAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var cart = mutableListOf<Cart>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartAdapter.CartViewHolder {
        val binding =
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartAdapter.CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.CartViewHolder, position: Int) {

        holder.binding.title.text = cart[position].product_name
        holder.binding.tvPrice.text = cart[position].price
        holder.binding.tvNum.text = cart[position].quantity.toString()
        Glide.with(holder.itemView.context).load(cart[position].image)
            .into(holder.binding.imgProduct)

        holder.binding.ivDelete.setOnClickListener {
            onItemClick.onDeleteClickListener(position)
        }

        holder.binding.add.setOnClickListener {
            onItemClick.onPlusClickListener(position)
        }

        holder.binding.remove.setOnClickListener {
            onItemClick.onMinusClickListener(position)
        }

    }

    override fun getItemCount(): Int {
        return cart.size
    }

    interface OnItemClick {
        fun onDeleteClickListener(position: Int) {}
        fun onPlusClickListener(position: Int) {}
        fun onMinusClickListener(position: Int) {}
    }
}
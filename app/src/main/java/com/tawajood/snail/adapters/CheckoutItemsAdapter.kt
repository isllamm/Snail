package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemCheckoutProductBinding
import com.tawajood.snail.pojo.Cart

class CheckoutItemsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<CheckoutItemsAdapter.CheckoutItemsViewHolder>() {
    var cart = mutableListOf<Cart>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class CheckoutItemsViewHolder(val binding: ItemCheckoutProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutItemsAdapter.CheckoutItemsViewHolder {
        val binding =
            ItemCheckoutProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CheckoutItemsAdapter.CheckoutItemsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CheckoutItemsAdapter.CheckoutItemsViewHolder,
        position: Int
    ) {

        holder.binding.title.text = cart[position].product_name
        holder.binding.tvPrice.text = cart[position].price
        holder.binding.ivNum.text = cart[position].quantity.toString()
        Glide.with(holder.itemView.context).load(cart[position].image)
            .into(holder.binding.imgProduct)


    }

    override fun getItemCount(): Int {
        return cart.size
    }

    interface OnItemClick {

    }
}
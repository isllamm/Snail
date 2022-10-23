package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemProductBinding
import com.tawajood.snail.pojo.Product
import com.tawajood.snail.pojo.ProductImages

class ProductsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    var products = mutableListOf<Product>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class ProductsViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ProductsViewHolder {
        val binding =
            ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductsAdapter.ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProductsAdapter.ProductsViewHolder,
        position: Int
    ) {

        holder.binding.tvName.text = products[position].name
        holder.binding.tvPrice.text = products[position].price.toString()+" $"
        if (products[position].images.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load("https://snail.horizon.net.sa/uploads/products_images/" + products[position].images[0].image)
                .into(holder.binding.ivProduct)
        }

        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
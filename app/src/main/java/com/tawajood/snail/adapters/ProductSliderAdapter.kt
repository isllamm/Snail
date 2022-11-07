package com.tawajood.snail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.tawajood.snail.databinding.ItemProductSliderBinding
import com.tawajood.snail.databinding.ItemSliderBinding
import com.tawajood.snail.pojo.ProductImages
import com.tawajood.snail.pojo.Slider

class ProductSliderAdapter(private val onItemClick: OnItemClick) :
    SliderViewAdapter<ProductSliderAdapter.ProductSliderViewHolder>() {

    class ProductSliderViewHolder(val binding: ItemProductSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root)

    var sliderItems = mutableListOf<ProductImages>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?): ProductSliderViewHolder {
        val binding =
            ItemProductSliderBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
        return ProductSliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ProductSliderViewHolder?, position: Int) {
        Glide.with(viewHolder!!.itemView).load("https://snail.horizon.net.sa/uploads/products_images/"+sliderItems[position].image)
            .into(viewHolder.binding.cover)

        viewHolder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getCount(): Int {
        return sliderItems.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
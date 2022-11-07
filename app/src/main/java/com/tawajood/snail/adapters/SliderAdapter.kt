package com.tawajood.snail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.tawajood.snail.databinding.ItemSliderBinding
import com.tawajood.snail.pojo.Slider

class SliderAdapter(private val onItemClick: OnItemClick) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    class SliderViewHolder(val binding: ItemSliderBinding) :
        SliderViewAdapter.ViewHolder(binding.root)

    var sliderItems = mutableListOf<Slider>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderViewHolder {
        val binding =
            ItemSliderBinding.inflate(LayoutInflater.from(parent!!.context), parent, false)
        return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder?, position: Int) {
        Glide.with(viewHolder!!.itemView).load(sliderItems[position].image)
            .into(viewHolder.binding.cover)
        viewHolder.binding.tvClinicName.text = sliderItems[position].name
        viewHolder.binding.ratingBar.rating = sliderItems[position].rating.toFloat()
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
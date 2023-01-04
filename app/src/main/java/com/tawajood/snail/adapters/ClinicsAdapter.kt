package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemClinicBinding
import com.tawajood.snail.pojo.Clinic
import com.tawajood.snail.utils.OnItemClickListener
import java.math.BigDecimal
import java.math.RoundingMode

class ClinicsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<ClinicsAdapter.ClinicViewHolder>() {

    var clinics = mutableListOf<Clinic>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ClinicViewHolder(val binding: ItemClinicBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicViewHolder {
        val binding = ItemClinicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ClinicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClinicViewHolder, position: Int) {
        val decimal = BigDecimal(clinics[position].rating.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        holder.binding.titleTv.text = clinics[position].name
        holder.binding.rating.rating = decimal.toFloat()
        Glide.with(holder.itemView)
            .load(clinics[position].image)
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return clinics.size
    }
    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }

}
package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tawajood.snail.databinding.ItemDayBinding
import com.tawajood.snail.databinding.ItemNewConsultationBinding
import com.tawajood.snail.pojo.Consultant
import com.tawajood.snail.pojo.Day
import com.tawajood.snail.utils.getDayMonthYearFormatDate

class MyNewConsultantsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<MyNewConsultantsAdapter.MyNewConsultantsViewHolder>() {

    var consultant = mutableListOf<Consultant>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    class MyNewConsultantsViewHolder(val binding: ItemNewConsultationBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyNewConsultantsAdapter.MyNewConsultantsViewHolder {
        val binding =
            ItemNewConsultationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyNewConsultantsAdapter.MyNewConsultantsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyNewConsultantsAdapter.MyNewConsultantsViewHolder,
        position: Int
    ) {

        holder.binding.tvId.text = consultant[position].id.toString()
        holder.binding.tvName.text = consultant[position].clinic.name
        holder.binding.orderDate.text = consultant[position].created_at
        holder.binding.tvType.text = consultant[position].requestType.name

        when (consultant[position].status) {
            0 -> {
                holder.binding.tvStatus.text = consultant[position].statusName
                holder.binding.container.setBackgroundColor(Color.parseColor("#F4CA89"))
            }
            1 -> {
                holder.binding.tvStatus.text = consultant[position].statusName
                holder.binding.container.setBackgroundColor(Color.parseColor("#635238"))

            }
            2 -> {
                holder.binding.container.setBackgroundColor(Color.parseColor("#FFF2E3"))
                holder.binding.tvStatus.text = consultant[position].statusName
            }
        }


        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return consultant.size
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
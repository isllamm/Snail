package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.R
import com.tawajood.snail.pojo.Notification
import com.tawajood.snail.utils.getDayMonthFormatDate

class NotificationsAdapter(private val onItemClick: OnItemClick) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder>() {

    companion object {
        const val NO_IMAGE = 0
        const val WITH_IMAGE = 1
    }

    var notifications = mutableListOf<Notification>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView = itemView.findViewById(R.id.title_tv)
        val bodyTv: TextView = itemView.findViewById(R.id.body_tv)
        val dateTv: TextView = itemView.findViewById(R.id.date_tv)
        val notImg: ImageView = itemView.findViewById(R.id.not_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        if (viewType == NO_IMAGE)
            return NotificationViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_notification, parent, false)
            )

        return NotificationViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_img_notification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.titleTv.text = notifications[position].title
        holder.bodyTv.text = notifications[position].body
        holder.dateTv.text = getDayMonthFormatDate(notifications[position].date)
        if (!notifications[position].image.isNullOrEmpty()) {
            Glide.with(holder.itemView)
                .load(notifications[position].image)
                .into(holder.notImg)
        }
        holder.itemView.setOnClickListener {
            onItemClick.onItemClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun getItemViewType(position: Int): Int {
        if (notifications[position].image.isNullOrEmpty())
            return NO_IMAGE
        return WITH_IMAGE
    }

    interface OnItemClick {
        fun onItemClickListener(position: Int)
    }
}
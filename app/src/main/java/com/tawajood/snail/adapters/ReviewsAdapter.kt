package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemClinicBinding
import com.tawajood.snail.databinding.ItemReviewBinding
import com.tawajood.snail.pojo.Review
import java.math.BigDecimal
import java.math.RoundingMode

class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {
    var reviews = mutableListOf<Review>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class ReviewViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsAdapter.ReviewViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewsAdapter.ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewsAdapter.ReviewViewHolder, position: Int) {
        val decimal = BigDecimal(reviews[position].rate.toDouble()).setScale(2, RoundingMode.HALF_EVEN)
        holder.binding.tvUsername.text = reviews[position].user.name
        holder.binding.ratingBar.rating = decimal.toFloat()
        holder.binding.tvReview.text = reviews[position].comment
        Glide.with(holder.itemView)
            .load(reviews[position].user.image)
            .into(holder.binding.ivUserImage)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }


}
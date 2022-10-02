package com.tawajood.snail.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ItemClinicBinding
import com.tawajood.snail.databinding.ItemReviewBinding
import com.tawajood.snail.pojo.Review

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
        holder.binding.tvUsername.text = reviews[position].name
        holder.binding.ratingBar.rating = reviews[position].rating
        holder.binding.tvReview.text = reviews[position].review
        Glide.with(holder.itemView)
            .load(reviews[position].img)
            .into(holder.binding.ivUserImage)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }


}
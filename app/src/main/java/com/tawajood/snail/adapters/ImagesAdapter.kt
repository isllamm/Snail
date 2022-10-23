package com.tawajood.snail.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anilokcun.uwmediapicker.model.UwMediaPickerMediaModel
import com.bumptech.glide.Glide
import com.tawajood.snail.databinding.ImageItemBinding
import java.io.File

class ImagesAdapter(private val onDeleteImageClickListener: OnDeleteImageClickListener) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    private var images = mutableListOf<UwMediaPickerMediaModel>()

    fun setImages(images: MutableList<UwMediaPickerMediaModel>) {
        this.images = images
        notifyDataSetChanged()
    }

    class ImageViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(File(images[position].mediaPath))
            .into(holder.binding.adImage)

        holder.binding.deleteIcon.setOnClickListener {
            onDeleteImageClickListener.onDeleteClickListener(position)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    interface OnDeleteImageClickListener {
        fun onDeleteClickListener(position: Int)
    }
}
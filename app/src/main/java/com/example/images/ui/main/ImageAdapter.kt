package com.example.images.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.images.beans.HitsItem
import com.example.images.databinding.ImageItemViewBinding

class ImageAdapter(private val context: Context, val images: List<HitsItem?>, private val onClickItem: OnClickItem)
    :RecyclerView.Adapter<ImageAdapter.ImageViewHolder>(){

        inner class ImageViewHolder(val binding: ImageItemViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = images[position]
        Glide.with(context).load(item?.largeImageURL.toString()).into(holder.binding.imageView)
        holder.binding.userTextView.text = item?.user
        holder.binding.root.setOnClickListener {
            onClickItem.onClickImage(item?.largeImageURL.toString())
        }


    }

    override fun getItemCount(): Int {
        return images.size
    }

    interface OnClickItem{
        fun onClickImage(imageUrl: String)
    }
}
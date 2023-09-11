package com.gmailclone.ui.main.fragments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.gmailclone.R

class ImagePagerAdapter : RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    private val images: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) = holder.bind(images[position])

    override fun getItemCount() = images.size

    @SuppressLint("notifyDataSetChanged")
    fun update(images: List<Int>) {
        this.images.apply {
            clear()
            addAll(images)
        }
        notifyDataSetChanged()
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        fun bind(imageResId: Int) {
            imageView.setImageResource(imageResId)
        }
    }
}

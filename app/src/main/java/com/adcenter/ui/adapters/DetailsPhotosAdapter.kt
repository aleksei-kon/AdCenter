package com.adcenter.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adcenter.R
import com.adcenter.extensions.layoutInflater
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.adapter_item_ad_photo.view.*

class DetailsPhotosAdapter(private val context: Context) :
    SliderViewAdapter<DetailsPhotosAdapter.SliderAdapterVH>() {

    private val inflater: LayoutInflater = context.layoutInflater

    private val photos = mutableListOf<String>()

    fun getPhotosList(): MutableList<String> = photos

    fun setPhotos(photos: List<String>) {
        this.photos.clear()
        this.photos.addAll(photos)

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH =
        SliderAdapterVH(inflater.inflate(R.layout.adapter_item_ad_photo, parent, false))

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, i: Int) {
        viewHolder.bind(photos[i])
    }

    override fun getCount(): Int = photos.size

    override fun getItemPosition(any: Any): Int = POSITION_NONE

    inner class SliderAdapterVH(private val itemView: View) :
        SliderViewAdapter.ViewHolder(itemView) {

        fun bind(photo: String) {
            Glide.with(context)
                .load(photo)
                .placeholder(R.drawable.default_placeholder)
                .into(itemView.photo)
        }
    }
}
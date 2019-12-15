package com.adcenter.ui.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.extensions.layoutInflater
import kotlinx.android.synthetic.main.adapter_item_new_photo.view.*

class NewPhotosAdapter(context: Context) :
    RecyclerView.Adapter<NewPhotosAdapter.PhotoViewHolder>() {

    private val items = mutableListOf<Uri>()

    fun addItems(uri: Uri) {
        items.add(uri)
    }

    private val inflater: LayoutInflater = context.layoutInflater

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(inflater.inflate(R.layout.adapter_item_new_photo, parent, false))

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(imageUri: Uri) {
            itemView.photo.setImageURI(imageUri)
        }
    }
}
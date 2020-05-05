package com.adcenter.ui.adapters

import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.extensions.layoutInflater
import kotlinx.android.synthetic.main.adapter_item_new_photo.view.*

class NewPhotosAdapter(
    private val removeItemClickListener: (Uri) -> Unit
) : RecyclerView.Adapter<NewPhotosAdapter.PhotoViewHolder>() {

    private val items = mutableListOf<Uri>()

    fun setItems(photos: List<Uri>) {
        items.clear()
        items.addAll(photos)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            parent.layoutInflater.inflate(R.layout.adapter_item_new_photo, parent, false)
        )

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class PhotoViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(imageUri: Uri) {
            itemView.photo.setImageURI(imageUri)
            itemView.deleteButton.setOnClickListener {
                removeItemClickListener.invoke(imageUri)
            }
        }
    }
}
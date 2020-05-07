package com.adcenter.ui.adapters

import android.view.View
import android.view.ViewGroup
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.layoutInflater
import com.adcenter.extensions.setTextWithVisibility
import com.adcenter.ui.adapters.ItemType.GRID
import com.adcenter.ui.adapters.ItemType.LINEAR
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.adapter_linear_ads_item.view.*

enum class ItemType {
    LINEAR,
    GRID
}

class AdsAdapter(
    private val type: ItemType,
    private val itemClickListener: (Int) -> Unit
) : BasePaginationAdapter<AdItemModel>() {

    override val itemLayout: Int = when (type) {
        LINEAR -> R.layout.adapter_linear_ads_item
        GRID -> R.layout.adapter_grid_ads_item
    }

    override val paginationLayout: Int = R.layout.adapter_pagination

    override fun setItems(items: List<AdItemModel>) {
        //val diffCallback = AdsDiffCallback(this.items, items)
        //val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
        //diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder = when (type) {
        LINEAR -> LinearItemViewHolder(parent.layoutInflater.inflate(itemLayout, parent, false))
        GRID -> GridItemViewHolder(parent.layoutInflater.inflate(itemLayout, parent, false))
    }

    override fun getPaginationViewHolder(parent: ViewGroup): PaginationViewHolder =
        PaginationViewHolder(parent.layoutInflater.inflate(paginationLayout, parent, false))

    inner class LinearItemViewHolder(view: View) : BaseItemViewHolder(view) {

        override fun bind(item: Any) {
            if (item is AdItemModel) {
                itemView.apply {
                    setOnClickListener { itemClickListener.invoke(item.id) }
                    title.setTextWithVisibility(item.title)
                    price.setTextWithVisibility(item.price)
                    place.setTextWithVisibility(item.place)
                    views.setTextWithVisibility(item.views)
                }

                val requestOptions = RequestOptions().transforms(
                    CenterCrop()
                )

                val thumbnail = Glide.with(itemView.context)
                    .load(R.drawable.default_placeholder)
                    .apply(requestOptions)

                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .apply(requestOptions)
                    .thumbnail(thumbnail)
                    .into(itemView.adPhoto)
            }
        }
    }

    inner class GridItemViewHolder(view: View) : BaseItemViewHolder(view) {

        override fun bind(item: Any) {
            if (item is AdItemModel) {
                itemView.apply {
                    title.setTextWithVisibility(item.title)
                    price.setTextWithVisibility(item.price)
                    setOnClickListener { itemClickListener.invoke(item.id) }
                }

                val requestOptions = RequestOptions().transforms(
                    CenterCrop()
                )

                val thumbnail = Glide.with(itemView.context)
                    .load(R.drawable.default_placeholder)
                    .apply(requestOptions)

                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .apply(requestOptions)
                    .thumbnail(thumbnail)
                    .into(itemView.adPhoto)
            }
        }
    }
}
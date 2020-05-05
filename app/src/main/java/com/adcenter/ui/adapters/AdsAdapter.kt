package com.adcenter.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.layoutInflater
import com.adcenter.extensions.setTextWithVisibility
import com.adcenter.ui.adapters.ItemType.GRID
import com.adcenter.ui.adapters.ItemType.LINEAR
import com.adcenter.ui.diffutill.AdsDiffCallback
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
        val diffCallback = AdsDiffCallback(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.items.clear()
        this.items.addAll(items)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder = when (type) {
        LINEAR -> LinearItemViewHolder(parent.layoutInflater.inflate(itemLayout, parent, false))
        GRID -> GridItemViewHolder(parent.layoutInflater.inflate(itemLayout, parent, false))
    }

    override fun getPaginationViewHolder(parent: ViewGroup): PaginationViewHolder =
        PaginationViewHolder(parent.layoutInflater.inflate(paginationLayout, parent, false))

    inner class LinearItemViewHolder(view: View) : BaseItemViewHolder(view) {

        private val animation: Animation = AnimationUtils.loadAnimation(
            itemView.context,
            R.anim.anim_fall_down
        )

        override fun bind(item: Any) {
            if (item is AdItemModel) {
                if (!animation.hasStarted() || animation.hasEnded()) {
                    itemView.startAnimation(animation)
                }

                itemView.apply {
                    title.setTextWithVisibility(item.title)
                    price.setTextWithVisibility(item.price)
                    place.setTextWithVisibility(item.place)
                    views.setTextWithVisibility(item.views)
                    adPhoto.load(item.photoUrl) {
                        placeholder(R.drawable.default_placeholder)
                        error(R.drawable.default_placeholder)
                    }
                    setOnClickListener { itemClickListener.invoke(item.id) }
                }
            }
        }
    }

    inner class GridItemViewHolder(view: View) : BaseItemViewHolder(view) {

        override fun bind(item: Any) {
            if (item is AdItemModel) {
                itemView.apply {
                    title.setTextWithVisibility(item.title)
                    price.setTextWithVisibility(item.price)
                    adPhoto.load(item.photoUrl) {
                        placeholder(R.drawable.default_placeholder)
                        error(R.drawable.default_placeholder)
                    }
                    setOnClickListener { itemClickListener.invoke(item.id) }
                }
            }
        }
    }
}
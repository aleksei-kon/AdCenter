package com.adcenter.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.layoutInflater
import com.adcenter.extensions.setTextWithVisibility
import com.adcenter.features.details.DetailsConstants.DETAILS_ID_KEY
import com.adcenter.ui.activities.DetailsActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_ads_item.view.*

class AdsAdapter(private val context: Context) : BasePaginationAdapter<AdItemModel>() {

    override val itemLayout: Int = R.layout.adapter_ads_item

    override val paginationLayout: Int = R.layout.adapter_pagination

    private val inflater: LayoutInflater = context.layoutInflater

    override fun setItems(items: Collection<AdItemModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder =
        ItemViewHolder(inflater.inflate(itemLayout, parent, false))

    override fun getPaginationViewHolder(parent: ViewGroup): PaginationViewHolder =
        PaginationViewHolder(inflater.inflate(paginationLayout, parent, false))

    inner class ItemViewHolder(view: View) : BaseItemViewHolder(view) {

        override fun bind(item: Any) {
            if (item is AdItemModel) {
                Glide.with(context)
                    .load(item.photoUrl)
                    .placeholder(R.drawable.ic_default_placeholder)
                    .into(itemView.adPhoto)

                itemView.apply {
                    title.setTextWithVisibility(item.title)
                    price.setTextWithVisibility(item.price)
                    place.setTextWithVisibility(item.place)
                    views.setTextWithVisibility(item.views)

                    setOnClickListener {
                        context.startActivity(
                            Intent(context, DetailsActivity::class.java).apply {
                                putExtra(DETAILS_ID_KEY, item.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
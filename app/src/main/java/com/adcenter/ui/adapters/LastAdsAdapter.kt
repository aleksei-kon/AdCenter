package com.adcenter.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adcenter.R
import com.adcenter.lastads.data.AdModel
import kotlinx.android.synthetic.main.adapter_last_ads_item.view.*

class LastAdsAdapter(private val context: Context) : BasePaginationAdapter<AdModel>() {

    override val itemLayout: Int = R.layout.adapter_last_ads_item

    override val paginationLayout: Int = R.layout.adapter_pagination

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun setItems(items: Collection<AdModel>) {
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
            if (item is AdModel) {
                itemView.title.text = item.name
            }
        }
    }
}
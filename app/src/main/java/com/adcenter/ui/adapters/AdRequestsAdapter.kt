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
import com.adcenter.utils.Constants
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.adapter_request_item.view.*

class AdRequestsAdapter(private val context: Context) : BasePaginationAdapter<AdItemModel>() {

    override val itemLayout: Int = R.layout.adapter_request_item

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
                val thumbnail = Glide.with(context)
                    .load(R.drawable.default_placeholder)
                    .apply(RequestOptions.circleCropTransform())

                Glide.with(context)
                    .load(item.photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .thumbnail(thumbnail)
                    .into(itemView.adPhoto)

                itemView.apply {
                    title.setTextWithVisibility(item.title)
                    date.setTextWithVisibility(item.date)

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
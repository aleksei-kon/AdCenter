package com.adcenter.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.layoutInflater
import com.adcenter.extensions.setTextWithVisibility
import com.adcenter.ui.diffutill.AdsDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.adapter_request_item.view.*

class AdRequestsAdapter(
    private val itemClickListener: (String) -> Unit
) : BasePaginationAdapter<AdItemModel>() {

    override val itemLayout: Int = R.layout.adapter_request_item

    override val paginationLayout: Int = R.layout.adapter_pagination

    override fun setItems(items: List<AdItemModel>) {
        val diffCallback = AdsDiffCallback(this.items, items)
        val diffRsult = DiffUtil.calculateDiff(diffCallback)

        this.items.clear()
        this.items.addAll(items)

        diffRsult.dispatchUpdatesTo(this)
    }

    override fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder =
        ItemViewHolder(parent.layoutInflater.inflate(itemLayout, parent, false))

    override fun getPaginationViewHolder(parent: ViewGroup): PaginationViewHolder =
        PaginationViewHolder(parent.layoutInflater.inflate(paginationLayout, parent, false))

    inner class ItemViewHolder(view: View) : BaseItemViewHolder(view) {

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
                    date.setTextWithVisibility(item.date)
                    setOnClickListener { itemClickListener.invoke(item.id) }
                }

                val thumbnail = Glide.with(itemView.context)
                    .load(R.drawable.default_placeholder)
                    .apply(RequestOptions.circleCropTransform())

                Glide.with(itemView.context)
                    .load(item.photoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .thumbnail(thumbnail)
                    .into(itemView.adPhoto)
            }
        }
    }
}
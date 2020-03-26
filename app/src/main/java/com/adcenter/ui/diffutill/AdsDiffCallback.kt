package com.adcenter.ui.diffutill

import androidx.recyclerview.widget.DiffUtil
import com.adcenter.entities.view.AdItemModel

class AdsDiffCallback(
    private val oldItems: List<AdItemModel>,
    private val newItems: List<AdItemModel>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]
}
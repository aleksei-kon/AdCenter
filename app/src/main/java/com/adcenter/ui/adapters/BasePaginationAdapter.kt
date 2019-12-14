package com.adcenter.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BasePaginationAdapter<T : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM = 0
    private val TYPE_PAGINATION = 1

    protected val items = mutableListOf<T>()

    protected abstract val itemLayout: Int

    protected abstract val paginationLayout: Int

    fun isEmpty() = items.isEmpty()

    abstract fun setItems(items: Collection<T>)

    abstract fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder

    abstract fun getPaginationViewHolder(parent: ViewGroup): RecyclerView.ViewHolder

    override fun getItemViewType(position: Int): Int =
        when {
            items.isEmpty() || position == items.size -> TYPE_PAGINATION
            else -> TYPE_ITEM
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_ITEM -> getItemViewHolder(parent)
            else -> getPaginationViewHolder(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseItemViewHolder) {
            holder.bind(items[position])
        }
    }

    override fun getItemCount() = items.size + 1

    abstract class BaseItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(item: Any)
    }

    class PaginationViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
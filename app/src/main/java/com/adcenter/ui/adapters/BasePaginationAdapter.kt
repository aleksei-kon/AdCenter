package com.adcenter.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BasePaginationAdapter<T : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_ITEM = 0
    private val TYPE_PAGINATION = 1

    private var isPagination: Boolean = false

    private val paginationPosition: Int
        get() = itemCount

    protected val items = mutableListOf<T>()

    protected abstract val itemLayout: Int

    protected abstract val paginationLayout: Int

    fun isEmpty() = items.isEmpty()

    fun showPagination() {
        if (!isPagination) {
            isPagination = true
            notifyItemInserted(paginationPosition)
        }
    }

    fun hidePagination() {
        if (isPagination) {
            isPagination = false
            notifyItemRemoved(paginationPosition)
        }
    }

    abstract fun setItems(items: List<T>)

    abstract fun getItemViewHolder(parent: ViewGroup): BaseItemViewHolder

    abstract fun getPaginationViewHolder(parent: ViewGroup): PaginationViewHolder

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
        when (holder) {
            is BaseItemViewHolder -> holder.bind(items[position])
        }
    }

    override fun getItemCount() = if (isPagination) {
        items.size + 1
    } else {
        items.size
    }

    abstract class BaseItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(item: Any)
    }

    open class PaginationViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
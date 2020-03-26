package com.adcenter.ui.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.ui.adapters.ViewHolderType.ITEM
import com.adcenter.ui.adapters.ViewHolderType.PAGINATION

enum class ViewHolderType {
    ITEM,
    PAGINATION
}

abstract class BasePaginationAdapter<T : Any> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

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
            items.isEmpty() || position == items.size -> PAGINATION.ordinal
            else -> ITEM.ordinal
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            ITEM.ordinal -> getItemViewHolder(parent)
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
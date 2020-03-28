package com.adcenter.ui.common

import androidx.recyclerview.widget.RecyclerView

class ScrollToEndListener(val onScrolledToEnd: () -> Unit) : RecyclerView.OnScrollListener() {
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (!recyclerView.canScrollVertically(1)) {
            recyclerView.removeOnScrollListener(this)
            onScrolledToEnd()
        }
    }
}
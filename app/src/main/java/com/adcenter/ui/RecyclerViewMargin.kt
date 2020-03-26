package com.adcenter.ui

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewMargin(
    private val margin: Int,
    private val columns: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)

        outRect.apply {
            right = margin
            bottom = margin

            if (position < columns) {
                top = margin
            }

            if (position % columns == 0) {
                left = margin
            }
        }
    }
}
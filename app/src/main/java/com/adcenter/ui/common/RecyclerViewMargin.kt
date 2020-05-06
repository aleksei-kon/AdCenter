package com.adcenter.ui.common

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewMargin(
    private val margin: Int,
    private val columns: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, position: Int, parent: RecyclerView) {
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
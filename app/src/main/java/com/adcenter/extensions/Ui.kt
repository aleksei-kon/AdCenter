package com.adcenter.extensions

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.withStyledAttributes

fun View.gone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.visible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.invisible() {
    if (visibility != View.INVISIBLE) {
        visibility = View.INVISIBLE
    }
}

fun TextView.setTextWithVisibility(text: String?) {
    if (text.isNullOrEmpty()) {
        gone()
    } else {
        visible()
    }

    this.text = text
}

fun View.setChildsEnabled(enabled: Boolean) {
    isEnabled = enabled

    if (this is ViewGroup) {
        for (i in 0 until childCount) {
            getChildAt(i).setChildsEnabled(enabled)
        }
    }
}

fun View.withStyledAttributes(
    attributeSet: AttributeSet? = null,
    styleArray: IntArray,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0,
    block: TypedArray.() -> Unit
) = context.withStyledAttributes(
    set = attributeSet,
    attrs = styleArray,
    defStyleAttr = defStyleAttr,
    defStyleRes = defStyleRes,
    block = block
)
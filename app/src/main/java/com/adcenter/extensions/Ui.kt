package com.adcenter.extensions

import android.view.View
import android.widget.TextView

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
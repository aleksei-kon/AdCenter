package com.adcenter.extensions

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView

val EditText.length: Int
    get() = text.toString().length

val EditText.isEmpty: Boolean
    get() = text.toString().isEmpty()

fun EditText.contains(char: Char): Boolean = text.toString().contains(char)

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
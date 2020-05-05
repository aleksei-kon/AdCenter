package com.adcenter.extensions

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.widget.Toast
import androidx.annotation.AttrRes
import androidx.fragment.app.Fragment
import com.adcenter.BuildConfig

fun Context.longToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.shortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Fragment.longToast(text: String) {
    requireContext().longToast(text)
}

fun Fragment.shortToast(text: String) {
    requireContext().shortToast(text)
}

fun <T> Context.attr(@AttrRes attr: Int, block: (TypedArray) -> T): T {
    val typedArray = obtainStyledAttributes(intArrayOf(attr))
    val result = block.invoke(typedArray)
    typedArray.recycle()

    return result
}

fun Context.colorFromAttr(@AttrRes attr: Int): Int {
    return when {
        BuildConfig.DEBUG -> {
            val color = attr(attr) { it.getColor(0, Color.GREEN) }

            if (color == Color.GREEN) {
                throw Exception("$color is wrong color attribute")
            }

            color
        }
        else -> attr(attr) { it.getColor(0, Color.WHITE) }
    }
}
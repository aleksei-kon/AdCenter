package com.adcenter.extensions

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

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
package com.adcenter.extensions

import android.view.LayoutInflater
import android.view.View

val View.layoutInflater: LayoutInflater
    get() = this.context.layoutInflater
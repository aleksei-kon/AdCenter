package com.adcenter.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel?> Fragment.provideViewModel(modelClass: Class<T>): T =
    ViewModelProviders.of(this).get(modelClass)

package com.adcenter.extensions

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

fun <T : ViewModel?> Fragment.provideViewModel(modelClass: Class<T>): T =
    ViewModelProviders.of(this).get(modelClass)

fun <T : ViewModel?> FragmentActivity.provideViewModel(modelClass: Class<T>): T =
    ViewModelProviders.of(this).get(modelClass)

fun <T : ViewModel?> Fragment.provideViewModel(
    modelClass: Class<T>,
    factory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, factory).get(modelClass)

fun <T : ViewModel?> FragmentActivity.provideViewModel(
    modelClass: Class<T>,
    factory: ViewModelProvider.Factory
): T = ViewModelProviders.of(this, factory).get(modelClass)
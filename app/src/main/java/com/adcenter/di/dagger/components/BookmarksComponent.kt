package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.annotations.FragmentScope
import com.adcenter.di.dagger.module.BookmarksModule
import com.adcenter.ui.fragments.BookmarksFragment
import dagger.Subcomponent

@Subcomponent(modules = [BookmarksModule::class])
@FragmentScope
interface BookmarksComponent {

    fun inject(fragment: BookmarksFragment)
}
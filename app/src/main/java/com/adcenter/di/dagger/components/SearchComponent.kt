package com.adcenter.di.dagger.components

import com.adcenter.di.dagger.module.SearchModule
import com.adcenter.di.dagger.scopes.SearchScope
import com.adcenter.features.search.viewmodel.SearchViewModel
import dagger.Subcomponent

@Subcomponent(modules = [SearchModule::class])
@SearchScope
interface SearchComponent {

    fun inject(viewModel: SearchViewModel)
}
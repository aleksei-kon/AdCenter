package com.adcenter.koin.dependencies

import com.adcenter.features.myads.repository.IMyAdsRepository
import com.adcenter.features.myads.repository.MyAdsRepository
import com.adcenter.features.myads.usecase.IMyAdsUseCase
import com.adcenter.features.myads.usecase.MyAdsUseCase
import com.adcenter.features.myads.viewmodel.MyAdsViewModel
import com.adcenter.ui.fragments.MyAdsFragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object MyAdsDependency {
    val module = module {
        scope(named<MyAdsFragment>()) {

            scoped<IMyAdsRepository> { MyAdsRepository() }

            scoped<IMyAdsUseCase> {
                MyAdsUseCase(
                    repository = get()
                )
            }

            viewModel {
                MyAdsViewModel(
                    myAdsUseCase = get()
                )
            }
        }
    }
}
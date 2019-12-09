package com.adcenter.koin

import com.adcenter.koin.dependencies.AppDependency
import com.adcenter.koin.dependencies.LastAdsDependency

val koinModules = listOf(
    AppDependency.module,
    LastAdsDependency.module
)
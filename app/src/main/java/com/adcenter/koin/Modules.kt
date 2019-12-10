package com.adcenter.koin

import com.adcenter.koin.dependencies.AppDependency
import com.adcenter.koin.dependencies.LastAdsDependency
import com.adcenter.koin.dependencies.MyAdsDependency

val koinModules = listOf(
    AppDependency.module,
    LastAdsDependency.module,
    MyAdsDependency.module
)
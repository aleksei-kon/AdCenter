package com.adcenter.koin

import com.adcenter.koin.dependencies.*

val koinModules = listOf(
    AppDependency.module,
    LastAdsDependency.module,
    MyAdsDependency.module,
    BookmarksDependency.module,
    SearchDependency.module
)
package com.adcenter.koin

import com.adcenter.koin.dependencies.*

val koinModules = listOf(
    AppDependency.module,
    LastAdsDependency.module,
    MyAdsDependency.module,
    BookmarksDependency.module,
    AdRequestsDependency.module,
    SearchDependency.module,
    DetailsDependency.module,
    NewDetailsDependency.module
)
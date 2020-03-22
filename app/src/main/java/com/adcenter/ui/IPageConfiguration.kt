package com.adcenter.ui

interface IPageConfiguration {

    val toolbarTitle: String

    enum class ToolbarScrollBehaviour {
        POSITIONED,
        DISAPPEARS
    }
}
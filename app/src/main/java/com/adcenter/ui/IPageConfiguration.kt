package com.adcenter.ui

interface IPageConfiguration {

    val toolbarTitle: String

    val toolbarScrollBehaviour: ToolbarScrollBehaviour

    enum class ToolbarScrollBehaviour {
        POSITIONED,
        DISAPPEARS
    }
}
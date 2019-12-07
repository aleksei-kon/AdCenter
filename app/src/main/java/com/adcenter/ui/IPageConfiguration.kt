package com.adcenter.ui

interface IPageConfiguration {

    fun getToolbarTitle(): String

    val toolbarScrollBehaviour: ToolbarScrollBehaviour

    enum class ToolbarScrollBehaviour {
        POSITIONED,
        DISAPPEARS
    }
}
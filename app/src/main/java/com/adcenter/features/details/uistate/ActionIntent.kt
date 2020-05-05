package com.adcenter.features.details.uistate

sealed class ActionIntent
data class ShowHideIntent(val detailsId: Int) : ActionIntent()
data class AddDeleteBookmarksIntent(val detailsId: Int) : ActionIntent()
data class DeleteIntent(val detailsId: Int) : ActionIntent()
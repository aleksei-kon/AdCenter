package com.adcenter.features.details.uistate

sealed class ActionUiState
object ShowHideProgress : ActionUiState()
object AddDeleteBookmarksProgress : ActionUiState()
object DeleteProgress : ActionUiState()
object ActionSuccess : ActionUiState()
data class ActionError(val throwable: Throwable) : ActionUiState()
package com.adcenter.features.bookmarks.uistate

import com.adcenter.features.bookmarks.models.BookmarksModel

sealed class BookmarksUiState
object Loading : BookmarksUiState()
object Pagination : BookmarksUiState()
object Updating : BookmarksUiState()
data class Success(val result: BookmarksModel) : BookmarksUiState()
data class Error(val throwable: Throwable) : BookmarksUiState()
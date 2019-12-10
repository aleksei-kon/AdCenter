package com.adcenter.bookmarks.uistate

import com.adcenter.bookmarks.data.BookmarksModel

sealed class BookmarksUiState {
    object Loading : BookmarksUiState()
    data class Success(val result: BookmarksModel) : BookmarksUiState()
    data class Error(val throwable: Throwable) : BookmarksUiState()
}
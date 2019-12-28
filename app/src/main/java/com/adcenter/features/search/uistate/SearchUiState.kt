package com.adcenter.features.search.uistate

import com.adcenter.features.search.data.SearchModel

sealed class SearchUiState {
    object NewSearch : SearchUiState()
    object Pagination : SearchUiState()
    data class Success(val result: SearchModel) : SearchUiState()
    data class Error(val throwable: Throwable) : SearchUiState()
}
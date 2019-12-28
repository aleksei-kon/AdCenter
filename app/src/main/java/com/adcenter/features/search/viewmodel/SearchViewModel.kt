package com.adcenter.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.search.SearchConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.search.data.SearchModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.usecase.ISearchUseCase
import com.adcenter.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchViewModel(private val searchUseCase: ISearchUseCase) : ViewModel(), CoroutineScope {

    private var currentParams: SearchRequestParams = SearchRequestParams()
    private var searchModel: SearchModel = SearchModel()
    private var pageNumber = FIRST_PAGE_NUMBER

    private val coroutineScopeJob = Job()
    private val searchUiMutableState = MutableLiveData<SearchUiState>()

    val searchData: LiveData<SearchUiState>
        get() = searchUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        if (currentParams.pageNumber != FIRST_PAGE_NUMBER) {
            searchUiMutableState.value = SearchUiState.Success(searchModel)
        }
    }

    fun load(searchText: String) {
        if (searchText != currentParams.searchText) {
            searchUiMutableState.value = SearchUiState.NewSearch

            searchModel = SearchModel()

            pageNumber = FIRST_PAGE_NUMBER
            currentParams = currentParams.copy(
                searchText = searchText,
                pageNumber = pageNumber
            )

            loadModel()
        }
    }

    fun loadMore() {
        currentParams = currentParams.copy(pageNumber = pageNumber)

        loadModel()
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        launch {
            when (val result = searchUseCase.load(currentParams)) {
                is Result.Success -> {
                    searchModel = mergeResults(searchModel, result.value)

                    searchUiMutableState.value = SearchUiState.Success(searchModel)

                    pageNumber++
                }
                is Result.Error -> {
                    searchUiMutableState.value = SearchUiState.Error(result.exception)
                }
            }
        }
    }

    private fun mergeResults(
        oldResponse: SearchModel,
        newResponse: SearchModel
    ): SearchModel {
        val itemsList = mutableListOf<AdItemModel>()

        itemsList += oldResponse.ads
        itemsList += newResponse.ads

        return SearchModel(itemsList)
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}
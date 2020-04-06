package com.adcenter.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.search.SearchConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.search.models.SearchModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.usecase.ISearchUseCase
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class SearchViewModel(
    private val useCase: ISearchUseCase
) : ViewModel() {

    private var currentParams: SearchRequestParams = SearchRequestParams()
    private var searchModel: SearchModel = SearchModel()
    private var disposable: Disposable? = null

    private val dataSource: Single<SearchModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(result.value)
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<SearchModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: SearchModel) {
            searchModel = model
            searchUiMutableState.value = SearchUiState.Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1
            )
        }

        override fun onError(e: Throwable) {
            searchUiMutableState.value = SearchUiState.Error(e)
        }
    }

    private val searchUiMutableState = MutableLiveData<SearchUiState>()

    val searchData: LiveData<SearchUiState>
        get() = searchUiMutableState

    fun load() {
        if (currentParams.pageNumber != FIRST_PAGE_NUMBER) {
            searchUiMutableState.value = SearchUiState.Success(searchModel)
        }
    }

    fun load(searchText: String) {
        if (searchText != currentParams.searchText) {
            searchUiMutableState.value = SearchUiState.NewSearch

            searchModel = SearchModel()
            currentParams = currentParams.copy(
                searchText = searchText,
                pageNumber = FIRST_PAGE_NUMBER
            )

            loadModel()
        }
    }

    fun loadMore() {
        searchUiMutableState.value = SearchUiState.Pagination
        loadModel()
    }

    private fun loadModel() {
        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}
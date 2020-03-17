package com.adcenter.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.extensions.async
import com.adcenter.features.search.SearchConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.search.data.SearchModel
import com.adcenter.features.search.data.SearchRequestParams
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.usecase.ISearchUseCase
import com.adcenter.utils.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchViewModel(private val searchUseCase: ISearchUseCase) : ViewModel() {

    private var currentParams: SearchRequestParams = SearchRequestParams()
    private var searchModel: SearchModel = SearchModel()
    private var disposable: Disposable? = null

    private val dataSource: Single<SearchModel>
        get() = Single.create {
            when (val result = searchUseCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(updateModel(result.value))
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<SearchModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: SearchModel) {
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

    private fun updateModel(
        newResponse: SearchModel
    ): SearchModel {
        searchModel = SearchModel(searchModel.ads + newResponse.ads)

        return searchModel
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}
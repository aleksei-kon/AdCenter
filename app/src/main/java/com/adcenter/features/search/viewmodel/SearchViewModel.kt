package com.adcenter.features.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.search.SearchConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.search.models.SearchModel
import com.adcenter.features.search.models.SearchRequestParams
import com.adcenter.features.search.uistate.*
import com.adcenter.features.search.usecase.ISearchUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class SearchViewModel(
    private val useCase: ISearchUseCase
) : ViewModel() {

    private var currentParams: SearchRequestParams = SearchRequestParams()
    private var searchModel: SearchModel = SearchModel()
    private var disposableBad = CompositeDisposable()

    private val dataSource: Single<SearchModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(result.value)
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val clearDbCall: Completable
        get() = Completable.create {
            try {
                useCase.clearDb()
                it.onComplete()
            } catch (e: Exception) {
                it.onError(e)
            }
        }

    private val observer = object : SingleObserver<SearchModel> {

        override fun onSubscribe(d: Disposable) {
            disposableBad.add(d)
        }

        override fun onSuccess(model: SearchModel) {
            searchModel = model
            searchUiMutableState.value = Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1,
                isForceUpdate = false
            )
        }

        override fun onError(e: Throwable) {
            searchUiMutableState.value = Error(e)
        }
    }

    private val searchUiMutableState = MutableLiveData<SearchUiState>()

    val searchData: LiveData<SearchUiState>
        get() = searchUiMutableState

    fun load() {
        if (currentParams.pageNumber != FIRST_PAGE_NUMBER) {
            searchUiMutableState.value = Success(searchModel)
        }
    }

    fun load(searchText: String) {
        if (searchText != currentParams.searchText) {
            searchUiMutableState.value = NewSearch

            searchModel = SearchModel()
            currentParams = currentParams.copy(
                searchText = searchText,
                pageNumber = FIRST_PAGE_NUMBER,
                isForceUpdate = false
            )

            disposableBad.clear()
            disposableBad.add(
                clearDbCall
                    .async()
                    .subscribe { loadModel() }
            )
        }
    }

    fun forceUpdate() {
        disposableBad.clear()
        currentParams = currentParams.copy(
            pageNumber = currentParams.pageNumber - 1,
            isForceUpdate = true
        )

        disposableBad.clear()
        disposableBad.add(
            clearDbCall
                .async()
                .subscribe { loadModel() }
        )
    }

    fun loadMore() {
        searchUiMutableState.value = Pagination
        disposableBad.clear()
        loadModel()
    }

    private fun loadModel() {
        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposableBad.clear()
        clearDbCall.async().subscribe()
    }
}
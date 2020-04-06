package com.adcenter.features.bookmarks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.bookmarks.models.BookmarksModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.features.bookmarks.uistate.BookmarksUiState
import com.adcenter.features.bookmarks.usecase.IBookmarksUseCase
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class BookmarksViewModel(
    private val useCase: IBookmarksUseCase
) : ViewModel() {

    private var currentParams: BookmarksRequestParams = BookmarksRequestParams()
    private var bookmarksModel: BookmarksModel = BookmarksModel()
    private var disposable: Disposable? = null

    private val dataSource: Single<BookmarksModel>
        get() = Single.create {
            when (val result = useCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(result.value)
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<BookmarksModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: BookmarksModel) {
            bookmarksModel = model
            bookmarksUiMutableState.value = BookmarksUiState.Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1
            )
        }

        override fun onError(e: Throwable) {
            bookmarksUiMutableState.value = BookmarksUiState.Error(e)
        }
    }

    private val bookmarksUiMutableState = MutableLiveData<BookmarksUiState>()

    val bookmarksData: LiveData<BookmarksUiState>
        get() = bookmarksUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
            bookmarksUiMutableState.value = BookmarksUiState.Loading
            loadModel()
        } else {
            bookmarksUiMutableState.value = BookmarksUiState.Success(bookmarksModel)
        }
    }

    fun loadMore() {
        bookmarksUiMutableState.value = BookmarksUiState.Pagination
        loadModel()
    }

    fun refresh() {
        bookmarksModel = BookmarksModel()
        currentParams = currentParams.copy(
            pageNumber = FIRST_PAGE_NUMBER
        )
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
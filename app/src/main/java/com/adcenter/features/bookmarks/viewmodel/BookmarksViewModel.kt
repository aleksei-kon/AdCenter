package com.adcenter.features.bookmarks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.extensions.async
import com.adcenter.features.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.bookmarks.models.BookmarksModel
import com.adcenter.features.bookmarks.models.BookmarksRequestParams
import com.adcenter.features.bookmarks.uistate.*
import com.adcenter.features.bookmarks.usecase.IBookmarksUseCase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class BookmarksViewModel(
    private val useCase: IBookmarksUseCase
) : ViewModel() {

    private var currentParams: BookmarksRequestParams = BookmarksRequestParams()
    private var bookmarksModel: BookmarksModel = BookmarksModel()
    private var disposableBad = CompositeDisposable()

    private val dataSource: Single<BookmarksModel>
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

    private val observer = object : SingleObserver<BookmarksModel> {

        override fun onSubscribe(d: Disposable) {
            disposableBad.add(d)
        }

        override fun onSuccess(model: BookmarksModel) {
            bookmarksModel = model
            bookmarksUiMutableState.value = Success(model)
            currentParams = currentParams.copy(
                pageNumber = currentParams.pageNumber + 1,
                isForceRefresh = false
            )
        }

        override fun onError(e: Throwable) {
            bookmarksUiMutableState.value = Error(e)
        }
    }

    private val bookmarksUiMutableState = MutableLiveData<BookmarksUiState>()

    val bookmarksData: LiveData<BookmarksUiState>
        get() = bookmarksUiMutableState

    fun load() {
        if (currentParams.pageNumber == FIRST_PAGE_NUMBER) {
            bookmarksUiMutableState.value = Loading
            disposableBad.clear()
            loadModel()
        } else {
            bookmarksUiMutableState.value = Success(bookmarksModel)
        }
    }

    fun loadMore() {
        bookmarksUiMutableState.value = Pagination
        disposableBad.clear()
        loadModel()
    }

    fun forceUpdate() {
        bookmarksUiMutableState.value = Updating

        disposableBad.clear()
        currentParams = currentParams.copy(
            pageNumber = currentParams.pageNumber - 1,
            isForceRefresh = true
        )
        loadModel()
    }

    fun refresh() {
        bookmarksUiMutableState.value = Updating

        disposableBad.clear()
        currentParams = currentParams.copy(
            pageNumber = FIRST_PAGE_NUMBER,
            isForceRefresh = true
        )
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
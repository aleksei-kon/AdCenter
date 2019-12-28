package com.adcenter.features.bookmarks.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.view.AdItemModel
import com.adcenter.features.bookmarks.BookmarksConstants.FIRST_PAGE_NUMBER
import com.adcenter.features.bookmarks.data.BookmarksModel
import com.adcenter.features.bookmarks.data.BookmarksRequestParams
import com.adcenter.features.bookmarks.uistate.BookmarksUiState
import com.adcenter.features.bookmarks.usecase.IBookmarksUseCase
import com.adcenter.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class BookmarksViewModel(private val bookmarksUseCase: IBookmarksUseCase) : ViewModel(),
    CoroutineScope {

    private var currentParams: BookmarksRequestParams = BookmarksRequestParams()
    private var bookmarksModel: BookmarksModel = BookmarksModel()
    private var pageNumber = FIRST_PAGE_NUMBER

    private val coroutineScopeJob = Job()
    private val bookmarksUiMutableState = MutableLiveData<BookmarksUiState>()

    val bookmarksData: LiveData<BookmarksUiState>
        get() = bookmarksUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        if (pageNumber == FIRST_PAGE_NUMBER) {
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
        pageNumber = FIRST_PAGE_NUMBER
        loadModel()
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        currentParams = BookmarksRequestParams(pageNumber)

        launch {
            when (val result = bookmarksUseCase.load(currentParams)) {
                is Result.Success -> {
                    bookmarksModel = mergeResults(bookmarksModel, result.value)

                    bookmarksUiMutableState.value = BookmarksUiState.Success(bookmarksModel)

                    pageNumber++
                }
                is Result.Error -> {
                    bookmarksUiMutableState.value = BookmarksUiState.Error(result.exception)
                }
            }
        }
    }

    private fun mergeResults(
        oldResponse: BookmarksModel,
        newResponse: BookmarksModel
    ): BookmarksModel {
        val itemsList = mutableListOf<AdItemModel>()

        itemsList += oldResponse.ads
        itemsList += newResponse.ads

        return BookmarksModel(itemsList)
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}
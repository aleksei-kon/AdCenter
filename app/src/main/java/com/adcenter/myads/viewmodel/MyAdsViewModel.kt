package com.adcenter.myads.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.AdModel
import com.adcenter.myads.MyAdsConstants.FIRST_PAGE_NUMBER
import com.adcenter.myads.data.MyAdsModel
import com.adcenter.myads.data.MyAdsRequestParams
import com.adcenter.myads.uistate.MyAdsUiState
import com.adcenter.myads.usecase.IMyAdsUseCase
import com.adcenter.utils.Result
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MyAdsViewModel(private val myAdsUseCase: IMyAdsUseCase) : ViewModel(), CoroutineScope {

    private lateinit var currentParams: MyAdsRequestParams
    private var myAdsModel: MyAdsModel = MyAdsModel()
    private var pageNumber = FIRST_PAGE_NUMBER

    private val coroutineScopeJob = Job()
    private val myAdsUiMutableState = MutableLiveData<MyAdsUiState>()

    val myAdsData: LiveData<MyAdsUiState>
        get() = myAdsUiMutableState

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + coroutineScopeJob

    fun load() {
        myAdsUiMutableState.value = MyAdsUiState.Loading
        loadModel()
    }

    fun loadMore() {
        loadModel()
    }

    fun refresh() {
        myAdsModel = MyAdsModel()
        pageNumber = FIRST_PAGE_NUMBER
        loadModel()
    }

    private fun loadModel() {
        coroutineContext.cancelChildren()

        currentParams = MyAdsRequestParams(pageNumber)

        launch {
            when (val result = myAdsUseCase.load(currentParams)) {
                is Result.Success -> {
                    myAdsModel = mergeResults(myAdsModel, result.value)

                    myAdsUiMutableState.value = MyAdsUiState.Success(myAdsModel)

                    pageNumber++
                }
                is Result.Error -> {
                    myAdsUiMutableState.value = MyAdsUiState.Error(result.exception)
                }
            }
        }
    }

    private fun mergeResults(
        oldResponse: MyAdsModel,
        newResponse: MyAdsModel
    ): MyAdsModel {
        val itemsList = mutableListOf<AdModel>()

        itemsList += oldResponse.ads
        itemsList += newResponse.ads

        return MyAdsModel(itemsList)
    }

    override fun onCleared() {
        super.onCleared()

        coroutineScopeJob.cancel()
    }
}
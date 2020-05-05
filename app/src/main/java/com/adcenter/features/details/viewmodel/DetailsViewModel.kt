package com.adcenter.features.details.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.entities.Result
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.Constants.EMPTY_ID
import com.adcenter.extensions.async
import com.adcenter.features.details.models.DetailsRequestParams
import com.adcenter.features.details.uistate.*
import com.adcenter.features.details.usecase.IActionUseCase
import com.adcenter.features.details.usecase.IDetailsUseCase
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class DetailsViewModel(
    private val detailsInfoUseCase: IDetailsUseCase,
    private val actionsUseCase: IActionUseCase
) : ViewModel() {

    private var currentParams: DetailsRequestParams = DetailsRequestParams()
    private var detailsModel: DetailsModel? = null
    private var disposableBag = CompositeDisposable()
    private var actionsDisposableBag = CompositeDisposable()

    private val dataSource: Single<DetailsModel>
        get() = Single.create {
            when (val result = detailsInfoUseCase.load(currentParams)) {
                is Result.Success -> it.onSuccess(result.value)
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<DetailsModel> {

        override fun onSubscribe(d: Disposable) {
            disposableBag.add(d)
        }

        override fun onSuccess(model: DetailsModel) {
            detailsModel = model
            detailsUiMutableState.value = Success(model)
        }

        override fun onError(e: Throwable) {
            detailsUiMutableState.value = Error(e)
        }
    }

    private fun getActionCall(intent: ActionIntent) = Completable.create {
        val result = when (intent) {
            is ShowHideIntent -> actionsUseCase.showHide(intent.detailsId)
            is AddDeleteBookmarksIntent -> actionsUseCase.addDeleteBookmark(intent.detailsId)
            is DeleteIntent -> actionsUseCase.delete(intent.detailsId)
        }

        when (result) {
            is Result.Success -> it.onComplete()
            is Result.Error -> it.onError(result.exception)
        }
    }

    private val actionObserver = object : CompletableObserver {

        override fun onComplete() {
            actionsUiMutableState.value = ActionSuccess
        }

        override fun onSubscribe(d: Disposable) {
            actionsDisposableBag.add(d)
        }

        override fun onError(e: Throwable) {
            actionsUiMutableState.value = ActionError(e)
        }
    }

    private val actionsUiMutableState = MutableLiveData<ActionUiState>()

    val actionsData: LiveData<ActionUiState>
        get() = actionsUiMutableState

    private val detailsUiMutableState = MutableLiveData<DetailsUiState>()

    val detailsData: LiveData<DetailsUiState>
        get() = detailsUiMutableState

    fun makeAction(intent: ActionIntent) {
        when (intent) {
            is ShowHideIntent -> actionsUiMutableState.value = ShowHideProgress
            is AddDeleteBookmarksIntent -> actionsUiMutableState.value = AddDeleteBookmarksProgress
            is DeleteIntent -> actionsUiMutableState.value = DeleteProgress
        }

        getActionCall(intent)
            .async()
            .subscribe(actionObserver)
    }

    fun load(detailsId: Int) {
        val detailsCopy = detailsModel

        when {
            detailsId == EMPTY_ID -> detailsUiMutableState.value = Error(Throwable())
            detailsCopy != null -> detailsUiMutableState.value = Success(detailsCopy)
            else -> {
                detailsUiMutableState.value = Loading
                currentParams = DetailsRequestParams(detailsId)
                loadModel()
            }
        }
    }

    fun update(detailsId: Int) {
        detailsModel = null

        when (detailsId) {
            EMPTY_ID -> detailsUiMutableState.value = Error(Throwable())
            else -> {
                currentParams = DetailsRequestParams(detailsId)
                loadModel()
            }
        }
    }

    private fun loadModel() {
        disposableBag.clear()

        dataSource
            .async()
            .subscribe(observer)
    }

    override fun onCleared() {
        super.onCleared()

        disposableBag.clear()
        actionsDisposableBag.clear()
    }
}
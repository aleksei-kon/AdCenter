package com.adcenter.features.newdetails.viewmodel

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.adcenter.extensions.async
import com.adcenter.features.newdetails.models.NewDetailsModel
import com.adcenter.features.newdetails.models.NewDetailsRequestParams
import com.adcenter.features.newdetails.uistate.NewDetailsUiState
import com.adcenter.features.newdetails.usecase.INewDetailsUseCase
import com.adcenter.features.newdetails.usecase.IUploadPhotoUseCase
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.datasource.Result
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import java.io.File

class NewDetailsViewModel(
    private val context: Context,
    private val newDetailsUseCase: INewDetailsUseCase,
    private val uploadPhotoUseCase: IUploadPhotoUseCase
) : ViewModel() {

    private var newDetailsModel: NewDetailsModel = NewDetailsModel()
    private var currentParams: NewDetailsRequestParams = NewDetailsRequestParams()
    private var photos = mutableListOf<Uri>()
    private var disposable: Disposable? = null

    private val dataSource: Single<NewDetailsModel>
        get() = Single.create {
            val photoUrls: List<String> = photos.map { getFile(it) }.map {
                when (val result = uploadPhotoUseCase.upload(it)) {
                    is Result.Success -> result.value
                    is Result.Error -> EMPTY
                }
            }

            currentParams = currentParams.copy(photos = photoUrls)

            when (val result = newDetailsUseCase.upload(currentParams)) {
                is Result.Success -> {
                    newDetailsModel = result.value
                    it.onSuccess(newDetailsModel)
                }
                is Result.Error -> it.onError(result.exception)
            }
        }

    private val observer = object : SingleObserver<NewDetailsModel> {

        override fun onSubscribe(d: Disposable) {
            disposable = d
        }

        override fun onSuccess(model: NewDetailsModel) {
            newDetailsUiMutableState.value = NewDetailsUiState.Success(model)
        }

        override fun onError(e: Throwable) {
            newDetailsUiMutableState.value = NewDetailsUiState.Error(e)
        }
    }

    private val newDetailsUiMutableState = MutableLiveData<NewDetailsUiState>()

    val newDetailsData: LiveData<NewDetailsUiState>
        get() = newDetailsUiMutableState

    fun addPhoto(uri: Uri) {
        photos.add(uri)
    }

    fun upload(params: NewDetailsRequestParams) {
        newDetailsUiMutableState.value = NewDetailsUiState.WaitLoading
        currentParams = params

        disposable?.dispose()

        dataSource
            .async()
            .subscribe(observer)
    }

    private fun getFile(uri: Uri): File = File(getRealPathFromURI(uri))

    private fun getRealPathFromURI(contentUri: Uri): String {
        return context.contentResolver.query(
            contentUri,
            arrayOf(MediaStore.Images.Media.DATA),
            null, null, null
        )?.use {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            it.moveToFirst()
            it.getString(columnIndex)
        } ?: EMPTY
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}
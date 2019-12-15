package com.adcenter.koin.dependencies

import com.adcenter.features.newdetails.repository.INewDetailsRepository
import com.adcenter.features.newdetails.repository.IPhotoRepository
import com.adcenter.features.newdetails.repository.NewDetailsRepository
import com.adcenter.features.newdetails.repository.PhotoRepository
import com.adcenter.features.newdetails.usecase.INewDetailsUseCase
import com.adcenter.features.newdetails.usecase.IUploadPhotoUseCase
import com.adcenter.features.newdetails.usecase.NewDetailsUseCase
import com.adcenter.features.newdetails.usecase.UploadPhotoUseCase
import com.adcenter.features.newdetails.viewmodel.NewDetailsViewModel
import com.adcenter.ui.activities.NewAdActivity
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

object NewDetailsDependency {
    val module = module {
        scope(named<NewAdActivity>()) {
            scoped<INewDetailsRepository> {
                NewDetailsRepository(
                    processor = get()
                )
            }
            scoped<IPhotoRepository> {
                PhotoRepository(
                    processor = get()
                )
            }
            scoped<INewDetailsUseCase> {
                NewDetailsUseCase(
                    repository = get()
                )
            }
            scoped<IUploadPhotoUseCase> {
                UploadPhotoUseCase(
                    repository = get()
                )
            }
            viewModel {
                NewDetailsViewModel(
                    context = androidContext(),
                    newDetailsUseCase = get(),
                    uploadPhotoUseCase = get(),
                    gson = get()
                )
            }
        }
    }
}
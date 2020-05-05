package com.adcenter.ui.activities

import android.graphics.Color.TRANSPARENT
import android.os.Bundle
import android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS
import android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.adcenter.R
import com.adcenter.appconfig.IAppConfig
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.*
import com.adcenter.extensions.Constants.EMPTY
import com.adcenter.extensions.Constants.EMPTY_ID
import com.adcenter.features.details.DetailsConstants.DETAILS_ID_KEY
import com.adcenter.features.details.DetailsConstants.IS_EDIT_PAGE
import com.adcenter.features.details.uistate.Error
import com.adcenter.features.details.uistate.Loading
import com.adcenter.features.details.uistate.Success
import com.adcenter.features.details.viewmodel.DetailsViewModel
import com.adcenter.ui.adapters.DetailsPhotosAdapter
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.layout_ad_details_info.*
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val layout: Int = R.layout.activity_details

    private val viewModel by lazy {
        provideViewModel(
            DetailsViewModel::class.java,
            viewModelFactory
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Injector
            .appComponent
            .detailsComponent()
            .inject(this)

        window.apply {
            clearFlags(FLAG_TRANSLUCENT_STATUS)
            addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = TRANSPARENT
        }

        backButton.setOnClickListener { finish() }

        setViewModelObserver()

        if (intent.hasExtra(DETAILS_ID_KEY)) {
            load(intent.getIntExtra(DETAILS_ID_KEY, EMPTY_ID))
        } else {
            noDataMessage.visible()
        }

        if (intent.hasExtra(IS_EDIT_PAGE) && intent.getBooleanExtra(IS_EDIT_PAGE, false)) {
            editButton.visible()
            deleteButton.visible()
        } else {
            editButton.gone()
            deleteButton.gone()
        }

        if (appConfig.isLoggedIn && !appConfig.isAdmin) {
            addRemoveBookmarkButton.visible()
        }

        if (appConfig.isAdmin) {
            showHideButton.visible()
        }
    }

    private fun setViewModelObserver() {
        viewModel.detailsData.observe(this, Observer {
            when (it) {
                is Loading -> {
                    progressBar.visible()
                    content.gone()
                    noDataMessage.gone()
                }
                is Success -> {
                    progressBar.gone()
                    noDataMessage.gone()
                    content.visible()
                    bindModel(it.result)
                }
                is Error -> {
                    progressBar.gone()
                    content.gone()
                    noDataMessage.visible()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }

    private fun bindModel(model: DetailsModel) {
        setRecyclerItems(model.photos)
        itemTitle.setTextWithVisibility(model.title)
        price.setTextWithVisibility(model.price)
        location.setTextWithVisibility(model.location)
        date.setTextWithVisibility(model.date)
        views.setTextWithVisibility(model.views)
        synopsis.setTextWithVisibility(model.synopsis)
        username.setTextWithVisibility(model.username)
        phone.setTextWithVisibility(model.phone)
    }

    private fun setRecyclerItems(items: List<String>) {
        val adapter = DetailsPhotosAdapter(this)
        postPhotosSlider.sliderAdapter = adapter

        if (items.isEmpty()) {
            adapter.setPhotos(listOf(EMPTY))
        } else {
            adapter.setPhotos(items)
        }
    }

    private fun load(detailsId: Int) = viewModel.load(detailsId)
}
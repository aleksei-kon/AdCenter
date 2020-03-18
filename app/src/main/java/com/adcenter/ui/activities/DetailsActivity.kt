package com.adcenter.ui.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.adcenter.R
import com.adcenter.app.App
import com.adcenter.config.IAppConfig
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.longToast
import com.adcenter.extensions.setTextWithVisibility
import com.adcenter.extensions.visible
import com.adcenter.features.details.DetailsConstants.DETAILS_ID_KEY
import com.adcenter.features.details.uistate.DetailsUiState
import com.adcenter.features.details.viewmodel.DetailsViewModel
import com.adcenter.ui.adapters.DetailsPhotosAdapter
import com.adcenter.ui.controllers.ShowHideButtonController
import com.adcenter.utils.Constants.EMPTY
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.layout_ad_details_info.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class DetailsActivity : BaseActivity() {

    @Inject
    lateinit var appConfig: IAppConfig

    init {
        App.appComponent.inject(this)
    }

    override val layout: Int = R.layout.activity_details

    private val viewModel: DetailsViewModel by viewModel {
        parametersOf(currentScope.id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }
        setViewModelObserver()

        if (intent.hasExtra(DETAILS_ID_KEY)) {
            load(intent.getStringExtra(DETAILS_ID_KEY) ?: EMPTY)
        } else {
            noDataMessage.visible()
        }
    }

    private fun showButtons() {
        if (appConfig.isLoggedIn && !appConfig.isAdmin) {
            addRemoveBookmarkButton.visible()
        }

        if (appConfig.isAdmin) {
            showHideButton.visible()
        }
    }

    private fun hideButtons() {
        addRemoveBookmarkButton.gone()
        showHideButton.gone()
    }

    private fun load(detailsId: String) = viewModel.load(detailsId)

    private fun setViewModelObserver() {
        viewModel.detailsData.observe(this, Observer {
            when (it) {
                is DetailsUiState.Loading -> {
                    progressBar.visible()
                    appbar.gone()
                    content.gone()
                    noDataMessage.gone()
                    hideButtons()
                }
                is DetailsUiState.Success -> {
                    progressBar.gone()
                    noDataMessage.gone()
                    appbar.visible()
                    content.visible()
                    showButtons()
                    bindModel(it.result)
                }
                is DetailsUiState.Error -> {
                    progressBar.gone()
                    appbar.gone()
                    content.gone()
                    hideButtons()
                    noDataMessage.visible()
                    it.throwable.message?.let { message -> longToast(message) }
                }
            }
        })
    }

    private fun bindModel(model: DetailsModel) {
        setRecyclerItems(model.photos)
        title = model.title
        price.setTextWithVisibility(model.price)
        location.setTextWithVisibility(model.location)
        date.setTextWithVisibility(model.date)
        views.setTextWithVisibility(model.views)
        synopsis.setTextWithVisibility(model.synopsis)
        username.setTextWithVisibility(model.username)
        phone.setTextWithVisibility(model.phone)

        showHideButton.setOnClickListener(
            ShowHideButtonController(
                button = showHideButton,
                id = model.id,
                isShown = model.isShown
            )
        )
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
}
package com.adcenter.ui.activities

import android.os.Bundle
import androidx.lifecycle.Observer
import com.adcenter.R
import com.adcenter.entities.view.DetailsModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.setTextWithVisibility
import com.adcenter.extensions.visible
import com.adcenter.features.details.DetailsConstants.DETAILS_ID_KEY
import com.adcenter.features.details.DetailsConstants.DETAILS_SCOPE_ID
import com.adcenter.features.details.uistate.DetailsUiState
import com.adcenter.features.details.viewmodel.DetailsViewModel
import com.adcenter.ui.adapters.PhotosAdapter
import com.adcenter.utils.Constants.EMPTY
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.layout_ad_details_info.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class DetailsActivity : BaseActivity() {

    override val layout: Int = R.layout.activity_details

    private val activityScope =
        getKoin().getOrCreateScope(DETAILS_SCOPE_ID, named<DetailsActivity>())

    private val viewModel: DetailsViewModel = activityScope.get()

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

    private fun load(detailsId: String) = viewModel.load(detailsId)

    private fun setViewModelObserver() {
        viewModel.detailsData.observe(this, Observer {
            when (it) {
                is DetailsUiState.Loading -> {
                    progressBar.visible()
                    appbar.gone()
                    content.gone()
                    bookmarkButton.gone()
                    noDataMessage.gone()
                }
                is DetailsUiState.Success -> {
                    progressBar.gone()
                    noDataMessage.gone()
                    appbar.visible()
                    content.visible()
                    bookmarkButton.visible()
                    bindModel(it.result)
                }
                is DetailsUiState.Error -> {
                    progressBar.gone()
                    appbar.gone()
                    content.gone()
                    bookmarkButton.gone()
                    noDataMessage.visible()
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
    }

    private fun setRecyclerItems(items: List<String>) {
        val adapter = PhotosAdapter(this)
        postPhotosSlider.sliderAdapter = adapter

        if (items.isEmpty()) {
            adapter.setPhotos(listOf(EMPTY))
        } else {
            adapter.setPhotos(items)
        }
    }
}
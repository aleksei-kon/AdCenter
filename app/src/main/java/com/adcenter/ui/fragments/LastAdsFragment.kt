package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.longToast
import com.adcenter.extensions.provideViewModel
import com.adcenter.extensions.visible
import com.adcenter.features.details.DetailsConstants
import com.adcenter.features.lastads.uistate.LastAdsUiState
import com.adcenter.features.lastads.viewmodel.LastAdsViewModel
import com.adcenter.resource.IResourceProvider
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdsAdapter
import kotlinx.android.synthetic.main.layout_recycler.*
import javax.inject.Inject

class LastAdsFragment : BaseFragment(), IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    init {
        Injector.appComponent.inject(this)
    }

    private lateinit var adapter: AdsAdapter

    override val layout: Int = R.layout.layout_recycler

    override val toolbarTitle: String
        get() = resourceProvider.lastAdsTitle

    private val viewModel by lazy {
        provideViewModel(LastAdsViewModel::class.java)
    }

    private val programsScrollListener = ScrollToEndListener {
        loadMore()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        initSwipeRefresh()
        load()
    }

    private fun initRecycler() {
        adapter = AdsAdapter(::onItemClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    private fun setViewModelObserver() {
        viewModel.lastAdsData.observe(viewLifecycleOwner, Observer { uiState ->
            when (uiState) {
                is LastAdsUiState.Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is LastAdsUiState.Pagination -> {
                    adapter.showPagination()
                }
                is LastAdsUiState.Success -> {
                    swipeRefresh.isRefreshing = false
                    adapter.hidePagination()
                    progressBar.gone()

                    if (uiState.result.ads.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    } else {
                        noDataMessage.gone()
                        recyclerView.visible()
                    }

                    setRecyclerItems(uiState.result.ads)
                    setScrollListener()
                }
                is LastAdsUiState.Error -> {
                    swipeRefresh.isRefreshing = false
                    adapter.hidePagination()
                    progressBar.gone()

                    if (adapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    uiState.throwable.message?.let { message -> requireContext().longToast(message) }
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        adapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(programsScrollListener)
    }

    private fun onItemClick(id: String) {
        context?.startActivity(
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsConstants.DETAILS_ID_KEY, id)
            }
        )
    }

    private fun load() = viewModel.load()

    private fun loadMore() = viewModel.loadMore()

    private fun refresh() = viewModel.refresh()
}
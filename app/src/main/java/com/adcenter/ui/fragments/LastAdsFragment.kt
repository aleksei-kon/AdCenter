package com.adcenter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.toast
import com.adcenter.extensions.visible
import com.adcenter.features.lastads.uistate.LastAdsUiState
import com.adcenter.features.lastads.viewmodel.LastAdsViewModel
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.utils.resource.IResourceProvider
import kotlinx.android.synthetic.main.layout_recycler.*
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LastAdsFragment : BaseFragment(), IPageConfiguration {

    private val resourceProvider: IResourceProvider by inject()

    private val viewModel: LastAdsViewModel by viewModel {
        parametersOf(currentScope.id)
    }

    private val programsScrollListener = ScrollToEndListener {
        loadMore()
        deleteScrollListener()
    }

    private lateinit var adapter: AdsAdapter

    override val layout: Int = R.layout.layout_recycler

    override val toolbarTitle: String = resourceProvider.lastAdsTitle

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        initSwipeRefresh()
        load()
    }

    private fun initRecycler() {
        adapter = AdsAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener {
            deleteScrollListener()
            refresh()
        }
    }

    private fun setViewModelObserver() {
        viewModel.lastAdsData.observe(viewLifecycleOwner, Observer {
            when (it) {
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

                    if (it.result.ads.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    } else {
                        noDataMessage.gone()
                        recyclerView.visible()
                    }

                    setRecyclerItems(it.result.ads)
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

                    it.throwable.message?.let { message -> requireContext().toast(message) }
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        adapter.setItems(items)
    }

    private fun deleteScrollListener() {
        recyclerView.clearOnScrollListeners()
    }

    private fun setScrollListener() {
        recyclerView.clearOnScrollListeners()
        recyclerView.addOnScrollListener(programsScrollListener)
    }

    private fun load() = viewModel.load()

    private fun loadMore() = viewModel.loadMore()

    private fun refresh() = viewModel.refresh()
}
package com.adcenter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import com.adcenter.features.adrequests.AdRequestsConstants.AD_REQUESTS_SCOPE_ID
import com.adcenter.features.adrequests.uistate.AdRequestsUiState
import com.adcenter.features.adrequests.viewmodel.AdRequestsViewModel
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.adapters.AdRequestsAdapter
import kotlinx.android.synthetic.main.layout_recycler.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class AdRequestsFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.ad_requests_title) }

    override val layout: Int = R.layout.layout_recycler

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val fragmentScope =
        getKoin().getOrCreateScope(AD_REQUESTS_SCOPE_ID, named<AdRequestsFragment>())

    private val viewModel: AdRequestsViewModel = fragmentScope.get()

    private lateinit var adapter: AdRequestsAdapter

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
            deleteScrollListener()
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        initViews()
        load()
    }

    private fun initRecycler() {
        adapter = AdRequestsAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun initViews() {
        swipeRefresh.setOnRefreshListener {
            refresh()
        }
    }

    private fun setViewModelObserver() {
        viewModel.adRequestsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is AdRequestsUiState.Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is AdRequestsUiState.Success -> {
                    swipeRefresh.isRefreshing = false
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
                is AdRequestsUiState.Error -> {
                    swipeRefresh.isRefreshing = false
                    progressBar.gone()

                    if (adapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

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
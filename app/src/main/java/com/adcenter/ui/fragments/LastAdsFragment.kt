package com.adcenter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.entities.AdModel
import com.adcenter.lastads.uistate.LastAdsUiState
import com.adcenter.lastads.viewmodel.LastAdsViewModel
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.lastads.LastAdsConstants.LAST_ADS_SCOPE_ID
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.extensions.gone
import com.adcenter.extensions.visible
import kotlinx.android.synthetic.main.layout_recycler.*
import org.koin.android.ext.android.getKoin
import org.koin.core.qualifier.named

class LastAdsFragment : BaseFragment(), IPageConfiguration {

    override fun getToolbarTitle(): String = getString(R.string.last_ads_title)

    override val layout: Int = R.layout.layout_recycler

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val fragmentScope =
        getKoin().getOrCreateScope(LAST_ADS_SCOPE_ID, named<LastAdsFragment>())

    private val viewModel: LastAdsViewModel = fragmentScope.get()

    private lateinit var adapter: AdsAdapter

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
        adapter = AdsAdapter(requireContext())
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
        viewModel.lastAdsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LastAdsUiState.Loading -> {
                    progressBar.visible()
                    recyclerView.gone()
                    noDataMessage.gone()
                }
                is LastAdsUiState.Success -> {
                    swipeRefresh.isRefreshing = false
                    progressBar.gone()
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(it.result.ads)
                    setScrollListener()
                }
                is LastAdsUiState.Error -> {
                    swipeRefresh.isRefreshing = false
                    progressBar.gone()
                    noDataMessage.visible()
                    recyclerView.visible()
                    setScrollListener()
                }
            }
        })
    }

    private fun setRecyclerItems(items: List<AdModel>) {
        if (items.isEmpty()) {
            noDataMessage.visible()
        } else {
            noDataMessage.gone()
            adapter.setItems(items)
        }
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
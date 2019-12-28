package com.adcenter.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.toast
import com.adcenter.extensions.visible
import com.adcenter.features.myads.uistate.MyAdsUiState
import com.adcenter.features.myads.viewmodel.MyAdsViewModel
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.adapters.AdsAdapter
import kotlinx.android.synthetic.main.layout_recycler.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MyAdsFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.my_ads_title) }

    override val layout: Int = R.layout.layout_recycler

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val viewModel: MyAdsViewModel by viewModel {
        parametersOf(currentScope.id)
    }

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
        viewModel.myAdsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is MyAdsUiState.Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is MyAdsUiState.Pagination -> {
                    adapter.showPagination()
                }
                is MyAdsUiState.Success -> {
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
                is MyAdsUiState.Error -> {
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
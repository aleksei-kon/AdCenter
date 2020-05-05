package com.adcenter.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adcenter.R
import com.adcenter.di.dagger.injector.Injector
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.longToast
import com.adcenter.extensions.provideViewModel
import com.adcenter.extensions.visible
import com.adcenter.features.adrequests.uistate.Error
import com.adcenter.features.adrequests.uistate.Loading
import com.adcenter.features.adrequests.uistate.Pagination
import com.adcenter.features.adrequests.uistate.Success
import com.adcenter.features.adrequests.viewmodel.AdRequestsViewModel
import com.adcenter.features.details.DetailsConstants
import com.adcenter.resource.IResourceProvider
import com.adcenter.ui.activities.DetailsActivity
import com.adcenter.ui.adapters.AdRequestsAdapter
import com.adcenter.ui.common.IPageConfiguration
import com.adcenter.ui.common.RecyclerViewMargin
import com.adcenter.ui.common.ScrollToEndListener
import kotlinx.android.synthetic.main.layout_recycler.*
import javax.inject.Inject

class AdRequestsFragment : BaseFragment(),
    IPageConfiguration {

    @Inject
    lateinit var resourceProvider: IResourceProvider

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val recyclerAdapter = AdRequestsAdapter(::onItemClick)

    private val viewModel by lazy {
        provideViewModel(
            AdRequestsViewModel::class.java,
            viewModelFactory
        )
    }

    private val recyclerScrollListener =
        ScrollToEndListener { loadMore() }

    override val layout: Int = R.layout.layout_recycler

    override val toolbarTitle: String
        get() = resourceProvider.adRequestsTitle

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Injector
            .appComponent
            .adRequestsComponent()
            .inject(this)

        initRecycler()
        initSwipeRefresh()
        setViewModelObserver()
        load()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.forceUpdate()
    }

    private fun initRecycler() {
        recyclerView.apply {
            addItemDecoration(
                RecyclerViewMargin(
                    margin = resources.getDimensionPixelSize(R.dimen.recycler_item_margin),
                    columns = 1
                )
            )
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recyclerAdapter
        }

        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.adRequestsData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Loading -> {
                    recyclerView.gone()
                    noDataMessage.gone()
                    progressBar.visible()
                }
                is Pagination -> {
                    recyclerAdapter.showPagination()
                }
                is Success -> {
                    swipeRefresh.isRefreshing = false
                    recyclerAdapter.hidePagination()
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
                is Error -> {
                    swipeRefresh.isRefreshing = false
                    recyclerAdapter.hidePagination()
                    progressBar.gone()

                    if (recyclerAdapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    it.throwable.message?.let { message -> longToast(message) }
                    setScrollListener()
                }
            }
        })
    }

    private fun initSwipeRefresh() {
        swipeRefresh.setOnRefreshListener { refresh() }
    }

    private fun setRecyclerItems(items: List<AdItemModel>) {
        recyclerAdapter.setItems(items)
    }

    private fun setScrollListener() {
        recyclerView.addOnScrollListener(recyclerScrollListener)
    }

    private fun onItemClick(id: Int) {
        startActivityForResult(
            Intent(context, DetailsActivity::class.java).apply {
                putExtra(DetailsConstants.DETAILS_ID_KEY, id)
            },
            123
        )
    }

    private fun load() = viewModel.load()

    private fun loadMore() = viewModel.loadMore()

    private fun refresh() = viewModel.refresh()
}
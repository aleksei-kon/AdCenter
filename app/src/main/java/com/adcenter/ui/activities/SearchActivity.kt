package com.adcenter.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.*
import com.adcenter.features.details.DetailsConstants
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.utils.EmptyTextWatcher
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : OfflineActivity() {

    override val layout: Int = R.layout.activity_search

    private val viewModel by lazy {
        provideViewModel(SearchViewModel::class.java)
    }

    private lateinit var adapter: AdsAdapter

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
        }

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            load()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        backButton.setOnClickListener { finish() }

        setViewModelObserver()
        initRecycler()
        searchText.addTextChangedListener(textWatcher)

        viewModel.load()
    }

    override fun onDestroy() {
        searchText.removeTextChangedListener(textWatcher)

        super.onDestroy()
    }

    override fun updateContentVisibility() {
        if (isConnectedToNetwork()) {
            offlineMessage.gone()
            content.visible()
        } else {
            content.gone()
            offlineMessage.visible()
        }
    }

    private fun initRecycler() {
        adapter = AdsAdapter(::onItemClick)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.searchData.observe(this, Observer {
            when (it) {
                is SearchUiState.NewSearch -> {
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                    adapter.showPagination()
                }
                is SearchUiState.Pagination -> {
                    adapter.showPagination()
                }
                is SearchUiState.Success -> {
                    adapter.hidePagination()

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
                is SearchUiState.Error -> {
                    adapter.hidePagination()

                    if (adapter.isEmpty()) {
                        recyclerView.gone()
                        noDataMessage.visible()
                    }

                    it.throwable.message?.let { message -> longToast(message) }
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
        startActivity(
            Intent(this, DetailsActivity::class.java).apply {
                putExtra(DetailsConstants.DETAILS_ID_KEY, id)
            }
        )
    }

    private fun load() = viewModel.load(searchText.text.toString())

    private fun loadMore() = viewModel.loadMore()
}
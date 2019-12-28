package com.adcenter.ui.fragments

import android.os.Bundle
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adcenter.R
import com.adcenter.entities.view.AdItemModel
import com.adcenter.extensions.gone
import com.adcenter.extensions.toast
import com.adcenter.extensions.visible
import com.adcenter.features.search.uistate.SearchUiState
import com.adcenter.features.search.viewmodel.SearchViewModel
import com.adcenter.ui.IPageConfiguration
import com.adcenter.ui.IPageConfiguration.ToolbarScrollBehaviour
import com.adcenter.ui.ScrollToEndListener
import com.adcenter.ui.adapters.AdsAdapter
import com.adcenter.utils.EmptyTextWatcher
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.scope.currentScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchFragment : BaseFragment(), IPageConfiguration {

    override val toolbarTitle: String by lazy { getString(R.string.search_title) }

    override val layout: Int = R.layout.fragment_search

    override val toolbarScrollBehaviour: ToolbarScrollBehaviour = ToolbarScrollBehaviour.DISAPPEARS

    private val viewModel: SearchViewModel by viewModel {
        parametersOf(currentScope.id)
    }

    private lateinit var adapter: AdsAdapter

    private val programsScrollListener: RecyclerView.OnScrollListener =
        ScrollToEndListener {
            loadMore()
            deleteScrollListener()
        }

    private val textWatcher: TextWatcher = object : EmptyTextWatcher() {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            load()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModelObserver()
        initRecycler()
        searchText.addTextChangedListener(textWatcher)
    }

    override fun onDestroyView() {
        searchText.removeTextChangedListener(textWatcher)

        super.onDestroyView()
    }

    private fun initRecycler() {
        adapter = AdsAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        setScrollListener()
    }

    private fun setViewModelObserver() {
        viewModel.searchData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is SearchUiState.NewSearch -> {
                    deleteScrollListener()
                    noDataMessage.gone()
                    recyclerView.visible()
                    setRecyclerItems(emptyList())
                }
                is SearchUiState.Success -> {
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

    private fun load() = viewModel.load(searchText.text.toString())

    private fun loadMore() = viewModel.loadMore()
}
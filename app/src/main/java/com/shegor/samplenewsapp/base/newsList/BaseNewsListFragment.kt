package com.shegor.samplenewsapp.base.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.base.news.BaseNewsFragment
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.coordinator.NewsCoordinator

abstract class BaseNewsListFragment<VM : BaseNewsListViewModel, B : ViewDataBinding, N : BaseNewsNavigator> :
    BaseNewsFragment<VM, B>() {

    private  var newsListAdapter: NewsListAdapter? = null
    private lateinit var navigator: BaseNewsNavigator
    protected lateinit var coordinator: NewsCoordinator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setupNavigation()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setNewsObservers()
    }

    private fun setupRecyclerView() {
        newsListAdapter = getNewsRvAdapter()
        getRecyclerView().apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsListAdapter
        }
    }

    private fun getNewsRvAdapter() =
        NewsListAdapter(NewsClickListener { newsItem, clickedViewId ->
            when (clickedViewId) {
                R.id.newsViewHolderContainer -> viewModel.navigateToDetailsFragment(newsItem)
                R.id.addRemoveToBookmarks -> viewModel.saveOrDeleteBookmark(newsItem)
            }
        })

    abstract override fun getRecyclerView(): RecyclerView

    protected open fun setNewsObservers() {

        viewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                newsListAdapter?.submitList(it)
            }
        })
    }

    private fun setupNavigation() {
        navigator = getNavigator()
        coordinator = NewsCoordinator(navigator)
    }

    protected abstract fun getNavigator(): N

    override fun onDestroyView() {
        super.onDestroyView()
        newsListAdapter = null
    }
}

package com.shegor.samplenewsapp.base.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.base.news.BaseNewsFragment
import com.shegor.samplenewsapp.coordinator.NewsCoordinator
import com.shegor.samplenewsapp.models.NewsModel

abstract class BaseNewsListFragment<VM : BaseNewsListViewModel, B : ViewDataBinding, N: BaseNewsNavigator> :
    BaseNewsFragment<VM, B>() {

    lateinit var newsListAdapter: NewsListAdapter
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
        NewsListAdapter(com.shegor.samplenewsapp.adapters.NewsClickListener { newsItem, clickedViewId ->
            NewsClickListener()
                .onClick(clickedViewId, newsItem, viewModel)
        })

    abstract override fun getRecyclerView(): RecyclerView

    open fun setNewsObservers() {

        viewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                newsListAdapter.submitList(it)
            }
        })
    }

    private fun setupNavigation(){
        navigator = getNavigator()
        coordinator = NewsCoordinator(navigator)
    }

    abstract fun getNavigator(): N


    class NewsClickListener {
        fun onClick(viewId: Int, newsItem: NewsModel, newsViewModel: BaseNewsListViewModel) {
            when (viewId) {
                R.id.newsViewHolderContainer -> newsViewModel.navigateToDetailsFragment(newsItem)
                R.id.addRemoveToBookmarks -> newsViewModel.saveOrDeleteBookmark(newsItem)
            }
        }
    }
}

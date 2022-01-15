package com.shegor.samplenewsapp.newsBookmarks

import android.os.Bundle
import android.view.View
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.*
import com.shegor.samplenewsapp.base.news.BottomMenuReselection
import com.shegor.samplenewsapp.base.NavigationSetting
import com.shegor.samplenewsapp.base.newsList.NewsListNavigator
import com.shegor.samplenewsapp.base.news.NewsRepoInitiating
import com.shegor.samplenewsapp.base.newsList.NewsRvAdapterInstantiating
import com.shegor.samplenewsapp.base.newsList.NewsListObserversSetting
import com.shegor.samplenewsapp.databinding.FragmentNewsBookmarksBinding

class NewsBookmarksFragment :
    BaseRecyclerViewFragment<NewsBookmarksViewModel, FragmentNewsBookmarksBinding, NewsListAdapter>(),
    BottomMenuReselection, NewsRepoInitiating, NewsRvAdapterInstantiating, NavigationSetting,
    NewsListObserversSetting {

    override val fragment = this

    override fun getViewModel() = NewsBookmarksViewModel::class.java

    override val layoutId = R.layout.fragment_news_bookmarks

    override fun getViewModelFactory() =
        BaseViewModelFactory(NewsBookmarksViewModel::class.java) { NewsBookmarksViewModel(getRepo()) }

    override fun getRvAdapter() = getNewsRvAdapter(viewModel)

    override fun getRecyclerView() = binding.newsRecyclerView

    override fun getNavigator(): NewsListNavigator = NewsBookmarksNavigator(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setItemReselectedListener(binding.newsRecyclerView)
        setObservers()

    }

    private fun setObservers() {
        setNavigationObserver(this)
        setNewsObservers(viewModel, viewLifecycleOwner, recyclerViewAdapter)
    }

    private fun connectDataBinding() {
        binding.newsBookmarksViewModel = viewModel
    }
}
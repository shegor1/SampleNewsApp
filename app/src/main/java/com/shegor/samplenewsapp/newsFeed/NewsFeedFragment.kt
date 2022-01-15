package com.shegor.samplenewsapp.newsFeed

import android.os.Bundle
import android.view.View
import com.shegor.samplenewsapp.base.internetNews.InternetNewsListObserversSetting
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.*
import com.shegor.samplenewsapp.base.news.BottomMenuReselection
import com.shegor.samplenewsapp.base.NavigationSetting
import com.shegor.samplenewsapp.base.news.NewsRepoInitiating
import com.shegor.samplenewsapp.base.newsList.NewsRvAdapterInstantiating
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedBinding
import com.shegor.samplenewsapp.service.NewsFilterCategory

class NewsFeedFragment :
    BaseRecyclerViewFragment<NewsFeedViewModel, FragmentNewsFeedBinding, NewsListAdapter>(),
    NewsRepoInitiating, NewsRvAdapterInstantiating, NavigationSetting, BottomMenuReselection,
    InternetNewsListObserversSetting {

    companion object {
        const val ARG_OBJECT = "filterCategory"
    }

    override val fragment = this

    override val layoutId = R.layout.fragment_news_feed

    override fun getViewModel() = NewsFeedViewModel::class.java

    override fun getViewModelFactory(): BaseViewModelFactory<NewsFeedViewModel> =
        BaseViewModelFactory(NewsFeedViewModel::class.java) {
            NewsFeedViewModel(
                getRepo(),
                arguments?.getParcelable(ARG_OBJECT) ?: NewsFilterCategory.GENERAL
            )
        }

    override fun getRvAdapter() = getNewsRvAdapter(viewModel)

    override fun getRecyclerView() = binding.newsRecyclerView

    override fun getNavigator() = NewsFeedNavigator(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setItemReselectedListener(binding.newsRecyclerView)
        setInternetNewsObservers(viewModel, recyclerViewAdapter)
        setNavigationObserver(this)
        setListeners()
    }

    private fun connectDataBinding() {
        binding.newsViewModel = viewModel
    }

    private fun setListeners() {
        binding.refreshLayout.setOnRefreshListener {
            viewModel.getLatestNewsData()
        }
    }
}

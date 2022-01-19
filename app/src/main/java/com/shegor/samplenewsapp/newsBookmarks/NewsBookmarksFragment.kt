package com.shegor.samplenewsapp.newsBookmarks

import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.base.BaseViewModelFactory
import com.shegor.samplenewsapp.base.newsList.BaseNewsListFragment
import com.shegor.samplenewsapp.databinding.FragmentNewsBookmarksBinding

class NewsBookmarksFragment :
    BaseNewsListFragment<NewsBookmarksViewModel, FragmentNewsBookmarksBinding, NewsBookmarksNavigator>() {

    override val layoutId = R.layout.fragment_news_bookmarks

    override fun getViewModel() = NewsBookmarksViewModel::class.java

    override fun getViewModelFactory() =
        BaseViewModelFactory(NewsBookmarksViewModel::class.java) {
            NewsBookmarksViewModel(newsRepo, coordinator::navigateToDetailsFragment)
        }

    override fun getRecyclerView() = binding.newsRecyclerView

    override fun getNavigator() = NewsBookmarksNavigator(requireActivity())

    override fun connectDataBinding() {
        super.connectDataBinding()
        binding.newsBookmarksViewModel = viewModel
    }
}
package com.shegor.samplenewsapp.newsBookmarks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.*
import com.shegor.samplenewsapp.databinding.FragmentNewsBookmarksBinding
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedBinding
import com.shegor.samplenewsapp.newsFeed.NewsFeedViewModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.ACCESS_VIEW_MODEL_ERROR_TEXT

class NewsBookmarksFragment :
    BaseRecyclerViewFragment<NewsBookmarksViewModel, FragmentNewsBookmarksBinding, NewsRepo, NewsListAdapter>(),
    BottomMenuReselection, NewsRepoInstantiating, NewsRvAdapterInstantiating, NewsNavigation,
    NewsListObserversSetting {

    override fun getViewModel() = NewsBookmarksViewModel::class.java

    override val layoutId = R.layout.fragment_news_bookmarks

    override fun getViewModelFactory() =
        BaseViewModelFactory(NewsBookmarksViewModel::class.java) { NewsBookmarksViewModel(getRepo()) }

    override fun getRvAdapter() = getNewsRvAdapter(viewModel)

    override fun getRecyclerView() = binding.newsRecyclerView

    override fun getNavigator(): NewsNavigator = NewsBookmarksNavigator(viewModel)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setItemReselectedListener(
            requireActivity(),
            lifecycleScope,
            findNavController(),
            binding.newsRecyclerView
        )
        setNavigationObserver(this)
        setNewsObservers(viewModel, viewLifecycleOwner, recyclerViewAdapter)
    }

    private fun connectDataBinding() {
        binding.newsBookmarksViewModel = viewModel
    }
}
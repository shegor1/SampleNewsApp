package com.shegor.samplenewsapp.base.newsList

import androidx.lifecycle.LifecycleOwner
import com.shegor.samplenewsapp.adapters.NewsListAdapter

interface NewsListObserversSetting {

    fun setNewsObservers(
        newsListViewModel: NewsListViewModel,
        viewLifecycleOwner: LifecycleOwner,
        recyclerViewAdapter: NewsListAdapter
    ) {

        newsListViewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                recyclerViewAdapter.submitList(it)
            }
        })
    }
}
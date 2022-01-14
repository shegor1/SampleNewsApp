package com.shegor.samplenewsapp.base.newsList

import androidx.lifecycle.LifecycleOwner
import com.shegor.samplenewsapp.adapters.NewsListAdapter

interface NewsListObserversSetting {

    fun setNewsObservers(
        internetNewsListViewModel: NewsListViewModel,
        viewLifecycleOwner: LifecycleOwner,
        recyclerViewAdapter: NewsListAdapter
    ) {

        internetNewsListViewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                recyclerViewAdapter.submitList(it)

            }
        })
    }
}
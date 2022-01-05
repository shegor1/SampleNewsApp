package com.shegor.samplenewsapp.base

import androidx.lifecycle.LifecycleOwner
import com.shegor.samplenewsapp.adapters.NewsListAdapter

interface InternetNewsListObserversSetting : NewsListObserversSetting {

    fun setInternetNewsObservers(
        internetNewsListViewModel: InternetNewsListViewModel,
        viewLifecycleOwner: LifecycleOwner,
        recyclerViewAdapter: NewsListAdapter
    ) {
        setNewsObservers(
            internetNewsListViewModel,
            viewLifecycleOwner,
            recyclerViewAdapter
        )

        internetNewsListViewModel.savedNewsLiveData.observe(viewLifecycleOwner) { dbNews ->
            val lastSavedNews = internetNewsListViewModel.savedNews ?: run {
                internetNewsListViewModel.savedNews = dbNews
                return@observe
            }

            if (dbNews != lastSavedNews) {

                internetNewsListViewModel.news.value?.let {

                    internetNewsListViewModel.checkIfSaved(it, dbNews)
                }
                internetNewsListViewModel.savedNews = dbNews
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

    }
}
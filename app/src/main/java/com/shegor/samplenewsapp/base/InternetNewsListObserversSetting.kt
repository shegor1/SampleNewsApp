package com.shegor.samplenewsapp.base

import androidx.lifecycle.LifecycleOwner
import com.shegor.samplenewsapp.adapters.NewsListAdapter

interface InternetNewsListObserversSetting {

    fun setInternetNewsObservers(
        internetNewsListViewModel: InternetNewsListViewModel,
        viewLifecycleOwner: LifecycleOwner,
        recyclerViewAdapter: NewsListAdapter
    ) {

        internetNewsListViewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                recyclerViewAdapter.submitList(it)

                println(recyclerViewAdapter.currentList)
            }
        })

        internetNewsListViewModel.savedNewsLiveData.observe(viewLifecycleOwner) {
            if (internetNewsListViewModel.savedNews == null) {
                internetNewsListViewModel.savedNews = it
            } else if (it != internetNewsListViewModel.savedNews) {
                internetNewsListViewModel.checkForDbChanges(it)
                internetNewsListViewModel.savedNews = it
                recyclerViewAdapter.notifyDataSetChanged()
            }
        }

    }
}
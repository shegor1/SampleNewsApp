package com.shegor.samplenewsapp.base

import androidx.lifecycle.LifecycleOwner
import com.shegor.samplenewsapp.base.InternetNewsListViewModel
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
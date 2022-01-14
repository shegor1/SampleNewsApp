package com.shegor.samplenewsapp.base.internetNews

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.base.newsList.NewsListObserversSetting
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

interface InternetNewsListObserversSetting : NewsListObserversSetting {

    val fragment: Fragment

    fun setInternetNewsObservers(
        internetNewsListViewModel: InternetNewsListViewModel,
        recyclerViewAdapter: NewsListAdapter
    ) {
        setNewsObservers(
            internetNewsListViewModel,
            fragment.viewLifecycleOwner,
            recyclerViewAdapter
        )

        internetNewsListViewModel.savedNewsLiveData.observe(fragment.viewLifecycleOwner) { dbNews ->
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

        internetNewsListViewModel.status.observe(fragment.viewLifecycleOwner, { status ->
            if (status == NewsLoadingStatus.INIT_ERROR || status == NewsLoadingStatus.REFRESHING_ERROR) {
                Toast.makeText(
                    fragment.context,
                    fragment.getString(R.string.network_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

    }
}
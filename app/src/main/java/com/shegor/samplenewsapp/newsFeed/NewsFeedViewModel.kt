package com.shegor.samplenewsapp.newsFeed

import androidx.lifecycle.LiveData
import com.shegor.samplenewsapp.base.networkNews.BaseNetworkNewsListViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.persistentStorage.UserPreferences
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES
import com.shegor.samplenewsapp.service.NewsFilterCategory

class NewsFeedViewModel(newsRepo: NewsRepo, private val category: NewsFilterCategory, navigateToDetails : ((newsItem: NewsModel) -> Unit)?) :
    BaseNetworkNewsListViewModel(newsRepo, navigateToDetails) {

    private val prefsLiveData: LiveData<UserPreferences> =  newsRepo.getPrefsLiveData()
    private lateinit var currentPrefs: UserPreferences


    private val prefsObserver: (prefs: UserPreferences) -> Unit = {
        currentPrefs = it
        getLatestNewsData()
    }

    init {
        setPrefsObserver()
    }

    private fun setPrefsObserver() {
        prefsLiveData.observeForever(prefsObserver)
    }

    fun getLatestNewsData() {

        _status.value = NewsLoadingStatus.LOADING

        getNewsData {
            newsRepo.getLatestNews(
                FILTER_COUNTRIES[currentPrefs.filterCountryStringId] ?: "ru",
                category
            )
        }
    }

    override fun onCleared() {
        prefsLiveData.removeObserver(prefsObserver)
        super.onCleared()
    }
}


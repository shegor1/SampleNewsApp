package com.shegor.samplenewsapp.newsFeed

import com.shegor.samplenewsapp.base.internetNews.InternetNewsListViewModel
import com.shegor.samplenewsapp.persistentStorage.UserPreferences
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES
import com.shegor.samplenewsapp.service.NewsFilterCategory

class NewsFeedViewModel(newsRepo: NewsRepo, private val category: NewsFilterCategory) :
    InternetNewsListViewModel(newsRepo) {

    private lateinit var currentPrefs: UserPreferences

    private val prefsObserver: (prefs: UserPreferences) -> Unit = {
        currentPrefs = it
        getLatestNewsData()
    }

    init {
        setPrefsObserver()
    }

    private fun setPrefsObserver() {
        prefs.userPreferencesLiveData.observeForever(prefsObserver)
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
        prefs.userPreferencesLiveData.removeObserver(prefsObserver)
        super.onCleared()
    }
}


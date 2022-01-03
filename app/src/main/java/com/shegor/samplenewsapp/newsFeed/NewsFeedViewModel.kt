package com.shegor.samplenewsapp.newsFeed

import com.shegor.samplenewsapp.base.InternetNewsListViewModel
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES
import com.shegor.samplenewsapp.service.NewsFilterCategory

class NewsFeedViewModel(newsRepo: NewsRepo, category: NewsFilterCategory) :
    InternetNewsListViewModel(newsRepo) {

    val prefsLiveData = prefs.userPreferencesFlow
    var filterCountry = prefsLiveData.value?.filterCountryStringId

    init {
        prefsLiveData.value?.let { getLatestNewsData(category) }
    }

    fun getLatestNewsData(category: NewsFilterCategory) {

        _status.value = NewsLoadingStatus.LOADING
        val filterCountry =
            FILTER_COUNTRIES[prefsLiveData.value?.filterCountryStringId] ?: "ru"

        getNewsData { newsRepo.getLatestNews(filterCountry, category) }
    }
}


package com.shegor.samplenewsapp.newsFeed

import androidx.lifecycle.*
import com.shegor.samplenewsapp.base.BaseRepository
import com.shegor.samplenewsapp.base.InternetNewsListViewModel
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES
import com.shegor.samplenewsapp.service.NewsFilterCategory


class NewsFeedViewModel( newsRepo: NewsRepo, category: NewsFilterCategory) :
     InternetNewsListViewModel(newsRepo) {

    val filterCountryLiveData = prefs.userPreferencesFlow
    var filterCountry = filterCountryLiveData.value?.filterCountryStringId

    init {
        filterCountryLiveData.value?.let { getNewsData(category) }
    }

    fun getNewsData(category: NewsFilterCategory) {

        _status.value = NewsLoadingStatus.LOADING
        val filterCountry =
            FILTER_COUNTRIES[filterCountryLiveData.value?.filterCountryStringId] ?: "ru"

        newsRepo.getAllNewsData(filterCountry, category, viewModelScope) { newsData ->
            when {
                newsData != null -> {
                    _news.value = newsData
                    _status.value = NewsLoadingStatus.DONE
                }
                _news.value == null -> _status.value = NewsLoadingStatus.INIT_ERROR
                else -> _status.value = NewsLoadingStatus.REFRESHING_ERROR
            }
        }
    }

    class Factory(private val repo: BaseRepository, private val category: NewsFilterCategory) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return when (modelClass.isAssignableFrom(NewsFeedViewModel::class.java)) {
                modelClass.isAssignableFrom(NewsFeedViewModel::class.java) -> NewsFeedViewModel(repo as NewsRepo, category) as T
                else -> throw IllegalArgumentException("ViewModelClass Not Found")
            }
        }
    }
}
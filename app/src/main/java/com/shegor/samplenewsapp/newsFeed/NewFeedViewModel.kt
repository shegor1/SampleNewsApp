package com.shegor.samplenewsapp.newsFeed

import android.app.Application
import androidx.lifecycle.*
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.prefs
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.FILTER_COUNTRIES
import com.shegor.samplenewsapp.service.NewsApi
import com.shegor.samplenewsapp.service.NewsFilterCategory


class NewFeedViewModel(application: Application, category: NewsFilterCategory) :
    AndroidViewModel(application) {

    var newsRepo = NewsRepo(
        NewsApi.newsRetrofitService
    )

    private val _status = MutableLiveData<NewsLoadingStatus>()
    val status: LiveData<NewsLoadingStatus>
        get() = _status

    private val _news = MutableLiveData<List<NewsModel>>()
    val news: LiveData<List<NewsModel>>
        get() = _news

    private val _navigationToDetailsFragment = MutableLiveData<NewsModel?>()
    val navigationToDetailsFragment: LiveData<NewsModel?>
        get() = _navigationToDetailsFragment

    val filterCountryLiveData = prefs.userPreferencesFlow
    var filterCountry = filterCountryLiveData.value?.filterCountryStringId

    val savedNewsLiveData = newsRepo.getNewsFromDb()
    var savedNews: List<NewsModel>? = null

    init {
        filterCountryLiveData.value?.let { getNewsData(category) }
    }


    fun getNewsData(category: NewsFilterCategory) {

        _status.value = NewsLoadingStatus.LOADING
        val filterCountry =
            FILTER_COUNTRIES[filterCountryLiveData.value?.filterCountryStringId] ?: "ru"

        newsRepo.getAllNewsData(filterCountry, category, viewModelScope) { newsData ->
            if (newsData != null) {
                _news.value = newsData
                _status.value = NewsLoadingStatus.DONE
            } else {
                if (_news.value == null)
                    _status.value = NewsLoadingStatus.INIT_ERROR
                else
                    _status.value = NewsLoadingStatus.REFRESHING_ERROR
            }
        }
    }


    fun navigateToDetailsFragment(newsItem: NewsModel) {
        _navigationToDetailsFragment.value = newsItem
    }

    fun finishNavigationToDetailsFragment() {
        _navigationToDetailsFragment.value = null
    }

    fun saveOrDeleteBookmark(newsItem: NewsModel) {
        val currentNews = _news.value?.find { newsItem == it }

        currentNews?.let {

            if (currentNews.saved) {

                currentNews.saved = false
                newsRepo.deleteNewsItem(currentNews)
            } else {

                currentNews.saved = true
                newsRepo.saveNewsItem(currentNews)
            }
        }
    }

    fun checkForDbChanges(dbNewsList: List<NewsModel>): List<NewsModel>? {
        val networkNewsList = news.value
        networkNewsList?.forEach { networkNewsItem ->
            networkNewsItem.saved = dbNewsList.contains(networkNewsItem)
        }
        return networkNewsList
    }


    class Factory(val app: Application, val cathegory: NewsFilterCategory) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewFeedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewFeedViewModel(app, cathegory) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }
}
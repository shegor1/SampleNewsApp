package com.shegor.samplenewsapp.newsSearch

import android.app.Application
import androidx.lifecycle.*
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi

class NewsSearchViewModel(application: Application) : ViewModel() {


    var newsRepo = NewsRepo(
        NewsApi.newsRetrofitService
    )

    private val _status = MutableLiveData<NewsLoadingStatus>()
    val status: LiveData<NewsLoadingStatus>
        get() = _status

    private val _news = MutableLiveData<List<NewsModel>>()
    val news: LiveData<List<NewsModel>>
        get() = _news

    private val _clearSearchBarAction = MutableLiveData<Boolean>()
    val clearSearchBarAction: LiveData<Boolean>
        get() = _clearSearchBarAction

    private val _navigationToDetailsFragment = MutableLiveData<NewsModel?>()
    val navigationToDetailsFragment: LiveData<NewsModel?>
        get() = _navigationToDetailsFragment

    init {
        _status.value = NewsLoadingStatus.SEARCH_INIT
    }

    fun searchNewsData(query: String) {

        _status.value = NewsLoadingStatus.LOADING
        newsRepo.getNewsDataByQuery(query, viewModelScope) { newsData ->
            _news.value = newsData
            if (newsData != null) {
                if (newsData.isEmpty())
                    _status.value = NewsLoadingStatus.NO_RESULTS
                else _status.value = NewsLoadingStatus.DONE
            } else _status.value = NewsLoadingStatus.INIT_ERROR

        }
    }

    fun navigateToDetailsFragment(newsItem: NewsModel) {
        _navigationToDetailsFragment.value = newsItem
    }

    fun finishNavigationToDetailsFragment() {
        _navigationToDetailsFragment.value = null
    }

    fun clearSearchBar() {
        _clearSearchBarAction.value = true
    }

    fun finishClearingSearchBar() {
        _clearSearchBarAction.value = false
    }

    fun saveOrDeleteBookmark(newsItem: NewsModel) {
        val currentNews = _news.value?.find { newsItem == it }
        currentNews?.let { currentNews ->
            if (newsItem.saved) {
                newsRepo.deleteNewsItem(currentNews)
                currentNews.saved = false
            } else {
                currentNews.saved = true
                newsRepo.saveNewsItem(currentNews)
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsSearchViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NewsSearchViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewModel")
        }
    }


}
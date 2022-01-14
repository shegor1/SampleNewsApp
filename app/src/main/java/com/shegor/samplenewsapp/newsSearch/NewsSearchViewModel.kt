package com.shegor.samplenewsapp.newsSearch

import androidx.lifecycle.*
import com.shegor.samplenewsapp.base.internetNews.InternetNewsListViewModel
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.repo.NewsRepo

class NewsSearchViewModel(repo: NewsRepo) : InternetNewsListViewModel(repo) {

    private val _clearSearchBarAction = MutableLiveData<Boolean>()
    val clearSearchBarAction: LiveData<Boolean>
        get() = _clearSearchBarAction


    init {
        _status.value = NewsLoadingStatus.SEARCH_INIT
    }

    fun getNewsDataByQuery(query: String) {

        _status.value = NewsLoadingStatus.LOADING

        getNewsData { newsRepo.getNewsDataByQuery(query) }
    }

    fun clearSearchBar() {
        _clearSearchBarAction.value = true
    }

    fun finishClearingSearchBar() {
        _clearSearchBarAction.value = false
    }
}
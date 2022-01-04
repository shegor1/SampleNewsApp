package com.shegor.samplenewsapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

abstract class NewsListViewModel(override val newsRepo: NewsRepo) : ViewModel(), SaveOrDeleteBookmark {

    abstract val news: LiveData<List<NewsModel>>

    protected val _status = MutableLiveData<NewsLoadingStatus>()
    val status: LiveData<NewsLoadingStatus>
        get() = _status

    private val _navigationToDetailsFragment = MutableLiveData<NewsModel?>()
    val navigationToDetailsFragment: LiveData<NewsModel?>
        get() = _navigationToDetailsFragment


    fun navigateToDetailsFragment(newsItem: NewsModel) {
        _navigationToDetailsFragment.value = newsItem
    }

    fun finishNavigationToDetailsFragment() {
        _navigationToDetailsFragment.value = null
    }
}
package com.shegor.samplenewsapp.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

abstract class NewsViewModel(protected open val newsRepo: NewsRepo) : ViewModel() {

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

    fun saveOrDeleteBookmark(newsItem: NewsModel) {

        newsItem.let {
            if (newsItem.saved) newsRepo.deleteNewsItem(newsItem) else newsItem.saved =
                true.also { newsRepo.saveNewsItem(newsItem) }
        }
    }
}
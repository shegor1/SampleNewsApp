package com.shegor.samplenewsapp.newsDetails


import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.newsDb
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi

class NewsDetailsViewModel(val currentNews: NewsModel, application: Application) : ViewModel() {

    var repo = NewsRepo(
        NewsApi.newsRetrofitService, newsDb
    )

    private val _bookmarkButtonState = MutableLiveData<Int>()
    val bookmarkButtonState: LiveData<Int>
        get() = _bookmarkButtonState

    fun saveOrDeleteNews() {
        if (!currentNews.saved) {
            currentNews.saved = true
            repo.saveNewsItem(currentNews)
            _bookmarkButtonState.value = R.drawable.ic_bookmark_saved
        } else {
            repo.deleteNewsItem(currentNews)
            currentNews.saved = false
            _bookmarkButtonState.value = R.drawable.ic_bookmark
        }
    }

    class NewsDetailsViewModelFactory(
        private val currentNews: NewsModel,
        private val application: Application
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("unchecked_cast")
            if (modelClass.isAssignableFrom(NewsDetailsViewModel::class.java)) {

                return NewsDetailsViewModel(currentNews, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
package com.shegor.samplenewsapp.newsDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.shegor.samplenewsapp.DeletedNews
import com.shegor.samplenewsapp.base.news.BaseNewsViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo

class NewsDetailsViewModel(override val newsRepo: NewsRepo, val currentNews: NewsModel) :
    BaseNewsViewModel(newsRepo) {

    private val _bookmarkButtonState = MutableLiveData(currentNews.saved)
    val bookmarkButtonState: LiveData<Boolean>
        get() = _bookmarkButtonState

    private val deletedNewsObserver: Observer<String> = Observer{ deletedNewsTitle ->
        if (deletedNewsTitle == currentNews.title) {
            currentNews.saved = false
            _bookmarkButtonState.value = false
        }
    }

    init {
        DeletedNews.newsToDelete.observeForever(deletedNewsObserver)
    }

    fun onBookmarkClick() {
        _bookmarkButtonState.value = !currentNews.saved
        saveOrDeleteBookmark(currentNews)
    }

    override fun onCleared() {
        DeletedNews.newsToDelete.removeObserver(deletedNewsObserver)
        super.onCleared()
    }
}


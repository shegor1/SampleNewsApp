package com.shegor.samplenewsapp.newsDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shegor.samplenewsapp.base.SaveOrDeleteBookmark
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo

class NewsDetailsViewModel(override val newsRepo: NewsRepo, val currentNews: NewsModel) :
    ViewModel(), SaveOrDeleteBookmark {

    private val _bookmarkButtonState = MutableLiveData<Boolean>()
    val bookmarkButtonState: LiveData<Boolean>
        get() = _bookmarkButtonState

    init {
        _bookmarkButtonState.value = currentNews.saved
    }

    fun onBookmarkClick() {
        _bookmarkButtonState.value = !currentNews.saved
        saveOrDeleteBookmark(currentNews)
    }
}


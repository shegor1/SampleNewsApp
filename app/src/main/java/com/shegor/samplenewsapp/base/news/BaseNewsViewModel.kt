package com.shegor.samplenewsapp.base.news

import androidx.lifecycle.ViewModel
import com.shegor.samplenewsapp.DeletedNews
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo

abstract class BaseNewsViewModel(open val newsRepo: NewsRepo) : ViewModel() {

    fun saveOrDeleteBookmark(newsItem: NewsModel) {

        newsItem.let {
            if (newsItem.saved) {

                DeletedNews.newsToDelete.value = newsItem.title
                newsRepo.deleteNewsItem(newsItem).also { newsItem.saved = false }
            } else newsItem.saved = true.also { newsRepo.saveNewsItem(newsItem) }
        }
    }
}
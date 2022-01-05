package com.shegor.samplenewsapp.base

import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo

interface SaveOrDeleteBookmark {

    val newsRepo: NewsRepo

    fun saveOrDeleteBookmark(newsItem: NewsModel) {

        newsItem.let {
            if (newsItem.saved) newsRepo.deleteNewsItem(newsItem)
                .also { newsItem.saved = false }
            else newsItem.saved = true.also { newsRepo.saveNewsItem(newsItem) }
        }
    }
}
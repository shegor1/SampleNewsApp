package com.shegor.samplenewsapp.newsBookmarks

import androidx.lifecycle.LiveData
import com.shegor.samplenewsapp.base.newsList.BaseNewsListViewModel
import com.shegor.samplenewsapp.models.NewsModel
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

class NewsBookmarksViewModel(
    newsRepo: NewsRepo,
    navigateToDetails: ((newsItem: NewsModel) -> Unit)?
) : BaseNewsListViewModel(newsRepo, navigateToDetails) {

    override val news = getSavedNews()

    private fun getSavedNews(): LiveData<List<NewsModel>> {

        _status.value = NewsLoadingStatus.LOADING

        return newsRepo.getLiveDataNewsFromDb().also { _status.value = NewsLoadingStatus.DONE }
    }
}
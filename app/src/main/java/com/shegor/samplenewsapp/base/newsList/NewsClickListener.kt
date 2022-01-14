package com.shegor.samplenewsapp.base.newsList

import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.newsList.NewsListViewModel
import com.shegor.samplenewsapp.models.NewsModel

class NewsClickListener {
    fun onClick(viewId: Int, newsItem: NewsModel, newsViewModel: NewsListViewModel) {
        when (viewId) {
            R.id.newsViewHolderContainer -> newsViewModel.navigateToDetailsFragment(newsItem)
            R.id.addRemoveToBookmarks -> newsViewModel.saveOrDeleteBookmark(
                newsItem
            )
        }
    }
}
package com.shegor.samplenewsapp.base

import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.models.NewsModel

interface NewsRvAdapterInstantiating {

    fun getNewsRvAdapter(clickListener: (NewsModel, Int) -> Unit): NewsListAdapter {
        return NewsListAdapter(NewsClickListener { newsItem, clickedViewId ->
            clickListener(newsItem, clickedViewId)
        })
    }
}
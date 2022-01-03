package com.shegor.samplenewsapp.base

import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter

interface NewsRvAdapterInstantiating {

    fun getNewsRvAdapter(viewModel: NewsViewModel): NewsListAdapter =
        NewsListAdapter(NewsClickListener { newsItem, clickedViewId ->
            NewsClickListener().onClick(clickedViewId, newsItem, viewModel)
        })
}

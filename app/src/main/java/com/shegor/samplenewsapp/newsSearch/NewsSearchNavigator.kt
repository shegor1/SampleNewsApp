package com.shegor.samplenewsapp.newsSearch

import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.models.NewsModel

class NewsSearchNavigator(private val fragmentActivity: FragmentActivity) : BaseNewsNavigator() {

    override fun navigateToDetailsFragment(newsItem: NewsModel) {

        fragmentActivity.findNavController(R.id.navHostFragment).navigate(
            NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsDetailsFragment(newsItem)
        )
    }
}
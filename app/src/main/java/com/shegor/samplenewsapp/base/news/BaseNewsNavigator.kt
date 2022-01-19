package com.shegor.samplenewsapp.base.news

import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.models.NewsModel

abstract class BaseNewsNavigator(private val fragmentActivity: FragmentActivity) {

    abstract fun navigateToDetailsFragment(newsItem: NewsModel)

    protected fun navigate(navigation: NavDirections) {
        fragmentActivity.findNavController(R.id.navHostFragment).navigate(navigation)
    }
}
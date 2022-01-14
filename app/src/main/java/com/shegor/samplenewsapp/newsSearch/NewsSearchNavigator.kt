package com.shegor.samplenewsapp.newsSearch

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.newsList.NewsListNavigator

class NewsSearchNavigator(viewModel: NewsSearchViewModel) : NewsListNavigator(viewModel) {

    override fun setupNavigation(fragment: Fragment) {

        setNavToDetailsFragmentObserver(fragment) {
            fragment.findNavController().navigate(
                NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsDetailsFragment(
                    it
                )
            )
        }
    }
}
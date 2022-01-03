package com.shegor.samplenewsapp.newsSearch

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.shegor.samplenewsapp.base.NewsNavigator

class NewsSearchNavigator(viewModel: NewsSearchViewModel) : NewsNavigator(viewModel) {

    override fun setupNavigation(fragment: Fragment) {

        setNavigationObserver(fragment) {
            fragment.findNavController().navigate(
                NewsSearchFragmentDirections.actionNewsSearchFragmentToNewsDetailsFragment(
                    it
                )
            )
        }
    }
}
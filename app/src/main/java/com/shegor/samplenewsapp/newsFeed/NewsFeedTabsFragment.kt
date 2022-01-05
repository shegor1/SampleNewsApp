package com.shegor.samplenewsapp.newsFeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.adapters.CategoryAdapter
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedTabsBinding
import com.shegor.samplenewsapp.service.NewsFilterCategory


class NewsFeedTabsFragment : Fragment() {

    private lateinit var binding: FragmentNewsFeedTabsBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_news_feed_tabs,
                container,
                false
            )

        setupViewPager()
        setupTabs()

        return binding.root
    }

    private fun setupViewPager() {
        categoryAdapter = CategoryAdapter(this)
        binding.pager.adapter = categoryAdapter

    }

    private fun setupTabs() {
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->

            tab.text = if (NewsFilterCategory.values().size > position)
            requireContext().getString(NewsFilterCategory.values()[position].categoryNameResId)
            else ""
        }.attach()
    }
}
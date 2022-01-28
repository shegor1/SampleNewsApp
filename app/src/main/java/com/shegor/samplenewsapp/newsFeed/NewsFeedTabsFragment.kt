package com.shegor.samplenewsapp.newsFeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.adapters.CategoryAdapter
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedTabsBinding
import com.shegor.samplenewsapp.service.NewsFilterCategory


class NewsFeedTabsFragment : Fragment() {

    private var _binding: FragmentNewsFeedTabsBinding? = null
    private val binding get() = _binding!!
    private var categoryAdapter: CategoryAdapter? = null
    private var mediator: TabLayoutMediator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
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
        categoryAdapter = CategoryAdapter(this.childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.pager.adapter = categoryAdapter

    }

    private fun setupTabs() {
       mediator = TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->

            tab.text = if (NewsFilterCategory.values().size > position)
                requireContext().getString(NewsFilterCategory.values()[position].categoryNameResId)
            else ""
        }
           mediator?.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        mediator = null
        categoryAdapter = null
        _binding = null
    }
}
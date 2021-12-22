package com.shegor.samplenewsapp.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shegor.samplenewsapp.service.NewsFilterCategory
import com.shegor.samplenewsapp.newsFeed.NewsFeedFragment
import com.shegor.samplenewsapp.newsFeed.NewsFeedFragment.Companion.ARG_OBJECT

class CategoryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = NewsFilterCategory.values().size

    override fun createFragment(position: Int): Fragment {

        val category = NewsFilterCategory.values()[position]
        val fragment = NewsFeedFragment()

        fragment.arguments = Bundle().apply {
            putParcelable(ARG_OBJECT, category)
        }
        return fragment
    }
}
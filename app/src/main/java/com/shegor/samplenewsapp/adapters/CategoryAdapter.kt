package com.shegor.samplenewsapp.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.shegor.samplenewsapp.service.NewsApiFilterCategory
import com.shegor.samplenewsapp.ui.NewsFeedFragment
import com.shegor.samplenewsapp.ui.NewsFeedFragment.Companion.ARG_OBJECT

class CategoryAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = NewsApiFilterCategory.values().size

    override fun createFragment(position: Int): Fragment {

        val category = NewsApiFilterCategory.values()[position]
        val fragment = NewsFeedFragment()

        fragment.arguments = Bundle().apply {
            putParcelable(ARG_OBJECT, category)
        }
        return fragment
    }
}
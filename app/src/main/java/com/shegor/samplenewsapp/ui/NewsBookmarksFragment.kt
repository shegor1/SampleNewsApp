package com.shegor.samplenewsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.databinding.FragmentNewsBookmarksBinding
import com.shegor.samplenewsapp.viewModels.NewsBookmarkViewModel

class NewsBookmarksFragment : Fragment() {

    private lateinit var binding: FragmentNewsBookmarksBinding

    private lateinit var newsRecyclerViewAdapter: NewsListAdapter


    private val favoriteNewsViewModel: NewsBookmarkViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, NewsBookmarkViewModel.Factory(activity.application))
            .get(NewsBookmarkViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_bookmarks, container, false)

        connectDataBinding()
        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setItemReselectedListener()
        setObservers()
    }


    private fun connectDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.favoriteNewsViewModel = favoriteNewsViewModel
    }

    private fun setupRecyclerView() {
        newsRecyclerViewAdapter = NewsListAdapter(NewsClickListener { newsItem, clickedViewId ->
            when (clickedViewId) {
                R.id.newsViewHolderContainer -> favoriteNewsViewModel.navigateToDetailsFragment(
                    newsItem
                )
                R.id.addRemoveToBookmarks -> favoriteNewsViewModel.deleteBookmark(newsItem)
            }
        })

        binding.newsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = newsRecyclerViewAdapter
        }
    }

    private fun setItemReselectedListener() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavMenu)
            ?.setOnItemReselectedListener { item ->
                lifecycleScope.launchWhenResumed {
                    val navController = findNavController()
                    val reselectedDestinationId = item.itemId

                    if (navController.currentDestination?.id ?: 0 == reselectedDestinationId) {
                        binding.newsRecyclerView.layoutManager?.scrollToPosition(0)
                    } else {
                        findNavController().popBackStack(reselectedDestinationId, inclusive = false)
                    }
                }
            }
    }

    private fun setObservers() {
        favoriteNewsViewModel.news.observe(viewLifecycleOwner, { newsList ->
            if (newsList != null) {
                newsRecyclerViewAdapter.submitList(newsList)
                if (newsList.isEmpty())
                    binding.noNewsImg.visibility = View.VISIBLE
                else binding.noNewsImg.visibility = View.GONE
            }
        })

        favoriteNewsViewModel.navigationToDetailsFragment.observe(viewLifecycleOwner, { newsItem ->
            newsItem?.let { title ->
                this.findNavController().navigate(
                    NewsBookmarksFragmentDirections
                        .actionFavoriteNewsFragmentToNewsDetails(title)
                )
                favoriteNewsViewModel.navigationToDetailsFragmentDone()
            }
        })
    }
}
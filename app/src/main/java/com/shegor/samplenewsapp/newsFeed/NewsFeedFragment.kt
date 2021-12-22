package com.shegor.samplenewsapp.newsFeed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.utils.NewsLoadingStatus
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.databinding.FragmentNewsFeedBinding
import com.shegor.samplenewsapp.service.NewsFilterCategory
import com.shegor.samplenewsapp.utils.ACCESS_VIEW_MODEL_ERROR_TEXT

class NewsFeedFragment : Fragment() {

    companion object {
        const val ARG_OBJECT = "filterCategory"
    }

    private lateinit var binding: FragmentNewsFeedBinding

    private lateinit var newsRecyclerViewAdapter: NewsListAdapter
    private lateinit var category: NewsFilterCategory

    private val newsViewModel: NewFeedViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            ACCESS_VIEW_MODEL_ERROR_TEXT
        }
        category = arguments?.getParcelable(ARG_OBJECT) ?: NewsFilterCategory.GENERAL
        ViewModelProvider(this, NewFeedViewModel.Factory(activity.application, category))
            .get(NewFeedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_feed, container, false)

        connectDataBinding()
        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setItemReselectedListener()
        setObservers()
        setListeners()
    }


    private fun connectDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.newsViewModel = newsViewModel
    }

    private fun setupRecyclerView() {
        newsRecyclerViewAdapter = NewsListAdapter(NewsClickListener { newsItem, clickedViewId ->
            when (clickedViewId) {
                R.id.newsViewHolderContainer -> newsViewModel.navigateToDetailsFragment(newsItem)
                R.id.addRemoveToBookmarks -> newsViewModel.saveOrDeleteBookmark(newsItem)
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
                    val reselectedDestinationId = item.itemId
                    if (findNavController().currentDestination?.id ?: -1 == reselectedDestinationId) {
                        binding.newsRecyclerView.layoutManager?.scrollToPosition(0)
                    } else {
                        findNavController().popBackStack(reselectedDestinationId, inclusive = false)
                    }
                }
            }
    }

    private fun setObservers() {
        newsViewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                newsRecyclerViewAdapter.submitList(it)
            }
        })

        newsViewModel.navigationToDetailsFragment.observe(viewLifecycleOwner, { newsItem ->
            newsItem?.let { title ->
                findNavController().navigate(
                    NewsFeedTabsFragmentDirections.actionNewsFeedTabsFragmentToNewsDetailsFragment(
                        title
                    )
                )
                newsViewModel.finishNavigationToDetailsFragment()
            }
        })

        newsViewModel.filterCountryLiveData.observe(viewLifecycleOwner) {
            val currentFilterCountryId = newsViewModel.filterCountry
            val newFilterCountryId = it.filterCountryStringId

            if (newFilterCountryId != currentFilterCountryId ?: -1) {
                newsViewModel.filterCountry = newFilterCountryId
                newsViewModel.getNewsData(category)
            }
        }

        newsViewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                NewsLoadingStatus.INIT_ERROR, NewsLoadingStatus.REFRESHING_ERROR -> {
                    Toast.makeText(
                        this.context,
                        getString(R.string.network_error_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        newsViewModel.savedNewsLiveData.observe(viewLifecycleOwner) {
            if (newsViewModel.savedNews == null) {
                newsViewModel.savedNews = it
            } else if (it != newsViewModel.savedNews) {
                    newsViewModel.checkForDbChanges(it)
                    newsViewModel.savedNews = it
                    newsRecyclerViewAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun setListeners() {
        binding.refreshLayout.setOnRefreshListener {
            newsViewModel.getNewsData(category)
        }
    }
}

package com.shegor.samplenewsapp.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.adapters.NewsClickListener
import com.shegor.samplenewsapp.adapters.NewsListAdapter
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.databinding.FragmentNewsSearchBinding
import com.shegor.samplenewsapp.utils.hideKeyboard
import com.shegor.samplenewsapp.viewModels.NewsSearchViewModel

class NewsSearchFragment : Fragment() {

    private lateinit var binding: FragmentNewsSearchBinding

    private lateinit var newsRecyclerViewAdapter: NewsListAdapter

    private val searchViewModel: NewsSearchViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, NewsSearchViewModel.Factory(activity.application))
            .get(NewsSearchViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_search, container, false)

        connectDataBinding()
        setupRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setItemReselectedListener()
        setObservers()
        setupListeners()
    }


    private fun connectDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchViewModel = searchViewModel
    }

    private fun setupRecyclerView() {
        newsRecyclerViewAdapter = NewsListAdapter(NewsClickListener { newsItem, clickedViewId ->
            when (clickedViewId) {
                R.id.newsViewHolderContainer -> searchViewModel.navigateToDetailsFragment(newsItem)
                R.id.addRemoveToBookmarks -> searchViewModel.saveOrDeleteBookmark(newsItem)
            }
        })

        binding.searchRecyclerView.apply {
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
                        binding.searchRecyclerView.layoutManager?.scrollToPosition(0)
                    } else {
                        findNavController().popBackStack(reselectedDestinationId, inclusive = false)
                    }
                }
            }
    }

    private fun setObservers() {
        searchViewModel.news.observe(viewLifecycleOwner, { newsList ->
            newsList?.let {
                newsRecyclerViewAdapter.submitList(it)
            }
        })

        searchViewModel.clearEditTextAction.observe(viewLifecycleOwner, { isClicked ->
            if (isClicked) {
                binding.searchField.text?.clear()
                searchViewModel.editTextCleared()
            }
        })

        searchViewModel.navigationToDetailsFragment.observe(viewLifecycleOwner, { newsItem ->
            newsItem?.let { title ->
                this.findNavController().navigate(
                    NewsSearchFragmentDirections
                        .actionSearchNewsFragmentToNewsDetails(newsItem)
                )
                searchViewModel.navigationToDetailsFragmentDone()
            }
        })
    }

    private fun setupListeners() {
        binding.searchField.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (v.text.isNotBlank()) {
                    searchViewModel.searchNewsData(v.text.toString())
                } else {
                    Toast.makeText(
                        this.context,
                        getString(R.string.empty_search_field),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                this.hideKeyboard()
                true
            } else {
                false
            }
        }

        binding.deleteQueryButton.setOnClickListener {
            searchViewModel.clearEditText()
        }
    }
}
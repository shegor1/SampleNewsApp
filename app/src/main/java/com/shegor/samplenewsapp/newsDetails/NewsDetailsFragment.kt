package com.shegor.samplenewsapp.newsDetails

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.BaseFragment
import com.shegor.samplenewsapp.base.BaseViewModelFactory
import com.shegor.samplenewsapp.base.news.BottomMenuReselection
import com.shegor.samplenewsapp.base.news.NewsRepoInitiating
import com.shegor.samplenewsapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : BaseFragment<NewsDetailsViewModel, FragmentNewsDetailsBinding>(),
    BottomMenuReselection, NewsRepoInitiating {

    override val fragment = this

    private lateinit var args: NewsDetailsFragmentArgs

    override fun getViewModel() = NewsDetailsViewModel::class.java

    override val layoutId = R.layout.fragment_news_details

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        args = NewsDetailsFragmentArgs.fromBundle(requireArguments())
        return BaseViewModelFactory(
            NewsDetailsViewModel::class.java
        ) {
            NewsDetailsViewModel(getRepo(), args.newsItem)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setItemReselectedListener(null)
        setListeners()
    }

    private fun connectDataBinding() {
        binding.currentNews = viewModel.currentNews
        binding.viewModel = viewModel
    }

    private fun setListeners() {

        binding.addRemoveToBookmarks.setOnClickListener {
            viewModel.onBookmarkClick()
        }

        binding.toolbar.setNavigationOnClickListener { view ->
            requireActivity().onBackPressed()
        }
    }


}
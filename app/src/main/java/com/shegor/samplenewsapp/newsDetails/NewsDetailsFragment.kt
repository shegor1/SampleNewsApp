package com.shegor.samplenewsapp.newsDetails

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.base.BaseViewModelFactory
import com.shegor.samplenewsapp.base.news.BaseNewsFragment
import com.shegor.samplenewsapp.databinding.FragmentNewsDetailsBinding

class NewsDetailsFragment : BaseNewsFragment<NewsDetailsViewModel, FragmentNewsDetailsBinding>() {

    private lateinit var args: NewsDetailsFragmentArgs

    override val layoutId = R.layout.fragment_news_details

    override fun getViewModel() = NewsDetailsViewModel::class.java

    override fun getViewModelFactory(): ViewModelProvider.Factory {
        args = NewsDetailsFragmentArgs.fromBundle(requireArguments())
        return BaseViewModelFactory(NewsDetailsViewModel::class.java) {
            NewsDetailsViewModel(newsRepo, args.newsItem)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectDataBinding()
        setListeners()
    }

    private fun connectDataBinding() {
        binding.currentNews = viewModel.currentNews
        binding.viewModel = viewModel
    }

    private fun setListeners() {

        binding.addRemoveToBookmarks.setOnClickListener { viewModel.onBookmarkClick() }

        binding.toolbar.setNavigationOnClickListener { view -> requireActivity().onBackPressed() }
    }
}
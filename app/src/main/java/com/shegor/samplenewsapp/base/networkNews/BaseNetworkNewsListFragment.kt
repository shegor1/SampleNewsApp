package com.shegor.samplenewsapp.base.networkNews

import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.news.BaseNewsNavigator
import com.shegor.samplenewsapp.base.newsList.BaseNewsListFragment
import com.shegor.samplenewsapp.utils.NewsLoadingStatus

abstract class BaseNetworkNewsListFragment<VM : BaseNetworkNewsListViewModel, B : ViewDataBinding, N: BaseNewsNavigator> :
    BaseNewsListFragment<VM, B, N>()  {

    override fun setNewsObservers() {
        super.setNewsObservers()

        viewModel.status.observe(viewLifecycleOwner, { status ->
            if (status == NewsLoadingStatus.INIT_ERROR || status == NewsLoadingStatus.REFRESHING_ERROR) {
                Toast.makeText(
                    context,
                    getString(R.string.network_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



}
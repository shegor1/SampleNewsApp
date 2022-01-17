package com.shegor.samplenewsapp.base.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.base.base.BaseFragment
import com.shegor.samplenewsapp.newsDb
import com.shegor.samplenewsapp.repo.NewsRepo
import com.shegor.samplenewsapp.service.NewsApi

abstract class BaseNewsFragment<VM : ViewModel, B : ViewDataBinding> : BaseFragment<VM, B>() {

    protected lateinit var newsRepo: NewsRepo

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        newsRepo = getRepo()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setItemReselectedListener()
    }

    open fun getRecyclerView(): RecyclerView? = null

    private fun getRepo() = NewsRepo(NewsApi.newsRetrofitService, newsDb)

    private fun setItemReselectedListener() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavMenu)
            ?.setOnItemReselectedListener {
                lifecycleScope.launchWhenResumed {

                    val currentDestination = findNavController().currentDestination

                    if (currentDestination?.parent?.startDestinationId ?: -1 == currentDestination?.id) {
                        getRecyclerView()?.let {
                            it.layoutManager?.scrollToPosition(0)
                        }
                    } else {
                        requireActivity().onBackPressed()
                    }
                }
            }
    }
}
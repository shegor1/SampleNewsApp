package com.shegor.samplenewsapp.ui

import android.os.Bundle
import android.view.*
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shegor.samplenewsapp.R
import com.shegor.samplenewsapp.databinding.FragmentNewsDetailsBinding
import com.shegor.samplenewsapp.viewModels.NewsDetailsViewModel

class NewsDetails : Fragment() {

    private lateinit var binding: FragmentNewsDetailsBinding
    private lateinit var args: NewsDetailsArgs

    private val detailsViewModel: NewsDetailsViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        args = NewsDetailsArgs.fromBundle(requireArguments())
        ViewModelProvider(
            this,
            NewsDetailsViewModel.NewsDetailsViewModelFactory(args.newsItem, activity.application)
        )
            .get(NewsDetailsViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news_details, container, false)

        connectDataBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setItemReselectedListener()
        setObservers()
        setListeners()
    }

    private fun setItemReselectedListener() {
        activity?.findViewById<BottomNavigationView>(R.id.bottomNavMenu)
            ?.setOnItemReselectedListener { item ->
                lifecycleScope.launchWhenResumed {
                    val reselectedDestinationId = item.itemId
                    findNavController().popBackStack(reselectedDestinationId, inclusive = false)
                }
            }
    }

    private fun setObservers() {
        detailsViewModel.bookmarkButtonState.observe(viewLifecycleOwner) { iconId ->
            binding.addRemoveToBookmarks.background =
                ResourcesCompat.getDrawable(resources, iconId, null)
        }
    }

    private fun setListeners() {

        binding.addRemoveToBookmarks.setOnClickListener {
            detailsViewModel.saveOrDeleteNews()
        }

        binding.toolbar.setNavigationOnClickListener { view ->
            requireActivity().onBackPressed()
        }
    }

    private fun connectDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        detailsViewModel.currentNews?.let {
            binding.currentNews = it
        }
    }
}
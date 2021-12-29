package com.shegor.samplenewsapp.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewFragment<
        VM : ViewModel,
        F : ViewModelProvider.Factory,
        B : ViewDataBinding,
        R : BaseRepository,
        RVA : RecyclerView.Adapter<*>
        > : BaseFragment<VM, F, B, R>() {

    protected lateinit var recyclerViewAdapter: RVA

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = getRvAdapter()

        getRecyclerView().apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }

    abstract fun getRvAdapter(): RVA
    abstract fun getRecyclerView(): RecyclerView
}
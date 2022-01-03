package com.shegor.samplenewsapp.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewFragment<
        VM : ViewModel,
        B : ViewDataBinding,
        R : BaseRepository,
        A : RecyclerView.Adapter<*>
        > : BaseFragment<VM, B, R>() {

    protected lateinit var recyclerViewAdapter: A

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

    abstract fun getRvAdapter(): A
    abstract fun getRecyclerView(): RecyclerView
}
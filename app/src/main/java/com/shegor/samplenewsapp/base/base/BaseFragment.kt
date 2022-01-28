package com.shegor.samplenewsapp.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<VM : ViewModel, B : ViewDataBinding> : Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!
    protected lateinit var viewModel: VM


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, getViewModelFactory())[getViewModel()]
        connectDataBinding()
    }

    protected abstract val layoutId: Int

    protected abstract fun getViewModel(): Class<VM>

    protected abstract fun getViewModelFactory(): ViewModelProvider.Factory

    protected open fun connectDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
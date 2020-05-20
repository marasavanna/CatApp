package com.example.catapp.bases

import androidx.databinding.ViewDataBinding


abstract class BaseViewModelFragment<DB : ViewDataBinding, VM : BaseViewModel> :
    BaseFragment<DB>() {

    abstract val viewModel: VM

    fun startLoading() {
        viewModel.isLoading.set(true)
    }

    fun stopLoading() {
        viewModel.isLoading.set(false)
    }
}
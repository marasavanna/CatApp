package com.example.catapp.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.catapp.utils.ToolbarFragment
import com.example.catapp.utils.ToolbarManager

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    @get:LayoutRes
    abstract val layoutRes: Int

    open lateinit var binding: DB

    protected abstract fun builder(): ToolbarFragment?

    open fun init(inflater: LayoutInflater, container: ViewGroup) {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        builder()?.let { ToolbarManager(it).prepareToolbar() }
    }

    open fun init() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        container?.let {
            init(inflater, container)
        }
        init()
        return binding.root
    }
}
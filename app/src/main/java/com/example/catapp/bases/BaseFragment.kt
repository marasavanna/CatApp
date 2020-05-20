package com.example.catapp.bases

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.catapp.R
import com.example.catapp.utils.ToolbarFragment
import com.example.catapp.utils.ToolbarManager
import com.example.catapp.utils.hideKeyboard

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (findNavController().currentDestination?.id == R.id.loginFragment ||
                        findNavController().currentDestination?.id == R.id.catBreedsFragment
                    ) {
                        requireActivity().finish()
                    } else {
                        findNavController().popBackStack()
                    }
                    view?.hideKeyboard()
                }
            })
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
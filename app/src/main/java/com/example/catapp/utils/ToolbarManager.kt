package com.example.catapp.utils

import androidx.navigation.findNavController
import com.example.catapp.R


class ToolbarManager constructor(
    private var builder: ToolbarFragment
) {
    fun prepareToolbar() {
        builder.apply {
            if (resId != ToolbarFragment.NO_TOOLBAR) {
                toolbar?.let {
                    if (title != 0) {
                        it.setTitle(title)
                    }
                    activity?.let { _ ->
                        it.apply {
                            setNavigationIcon(R.drawable.ic_close_black_24dp)
                            setNavigationOnClickListener {
                                findNavController().navigateUp()
                            }
                        }
                    }
                }
            }
        }
    }
}
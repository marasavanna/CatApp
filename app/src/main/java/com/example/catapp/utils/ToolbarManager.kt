package com.example.catapp.utils

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
                    activity?.let { activity ->
                        it.apply {
                            setNavigationIcon(R.drawable.ic_close_black_24dp)
                            setNavigationOnClickListener {
                                activity.onBackPressed()
                            }
                        }
                    }
                }
            }
        }
    }
}
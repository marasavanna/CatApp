package com.example.catapp.utils

import androidx.annotation.IdRes
import androidx.annotation.MenuRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity

class ToolbarFragment(
    @IdRes val resId: Int,
    @StringRes val title: Int,
    var activity: FragmentActivity?,
    var toolbar: Toolbar? = null
) {
    class Builder {
        private var resId: Int = 0
        private var title: Int = 0
        private var activity: FragmentActivity? = null
        private var toolbar: Toolbar? = null

        fun shouldDisplayBack(activity: FragmentActivity) = apply { this.activity = activity }

        fun with(toolbar: Toolbar?) = apply { this.toolbar = toolbar }

        fun build() =
            ToolbarFragment(resId, title, activity, toolbar)
    }

    companion object {
        const val NO_TOOLBAR = -1
    }
}


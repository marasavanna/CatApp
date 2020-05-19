package com.example.catapp.utils

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.catapp.R

fun NavController.navigateIfAdded(
    fragment: Fragment,
    navDirections: NavDirections,
    currentDestinationId: Int
) {
    if (fragment.isAdded && currentDestinationId == currentDestination?.id) {
        navigate(navDirections)
    }
}

fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun Context.displayModalPopup(
    title: String?,
    message: String?,
    okAction: () -> Unit = {}
) {
    val builder = AlertDialog.Builder(this)

    title?.let {
        builder.setTitle(title)
    }

    message?.let {
        builder.setMessage(message)
    }

    builder.setNeutralButton(R.string.ok) { _, _ ->
        okAction()
    }

    builder.create().show()
}
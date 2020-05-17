package com.example.catapp.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections

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
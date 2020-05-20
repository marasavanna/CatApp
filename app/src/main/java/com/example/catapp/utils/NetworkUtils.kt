package com.example.catapp.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.catapp.R

object NetworkUtils {

    @Suppress("DEPRECATION")
    fun isNetworkAvailable(
        context: Context
    ): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isNetworkAvailable = networkInfo != null && networkInfo.isConnected
        if (!isNetworkAvailable) {
            showNoInternetConnectionDialog(context)
        }
        return isNetworkAvailable
    }

    private fun showNoInternetConnectionDialog(context: Context) {
        context.displayModalPopup(
            null,
            context.getString(R.string.no_internet_connection_dialog_message)
        )
    }
}
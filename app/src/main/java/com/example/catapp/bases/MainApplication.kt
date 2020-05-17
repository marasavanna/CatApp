package com.example.catapp.bases

import android.app.Application
import com.example.catapp.di.viewModelModule
import networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(networkModule, viewModelModule))
        }
    }
}
package com.example.catapp.bases

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {

    val isLoading = ObservableBoolean(false)
}
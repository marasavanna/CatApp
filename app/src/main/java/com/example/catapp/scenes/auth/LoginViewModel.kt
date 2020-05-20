package com.example.catapp.scenes.auth

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.bases.BaseViewModel
import com.example.catapp.model.User
import com.example.catapp.repository.LoginRepository
import okhttp3.ResponseBody
import java.lang.Exception

class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

    val loginError: LiveData<Exception>
        get() = _loginError
    private val _loginError = MutableLiveData<Exception>()

    val loginResponse: LiveData<ResponseBody>
        get() = _loginResponse
    private val _loginResponse = MutableLiveData<ResponseBody>()

    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val isLoading = ObservableBoolean(false)

    fun login(email: String, password: String) {
        loginRepository.login(email, password, _loginError, _loginResponse)
    }
}
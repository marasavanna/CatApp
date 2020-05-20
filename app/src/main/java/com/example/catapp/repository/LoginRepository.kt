package com.example.catapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.LoginApiService
import com.example.catapp.model.User
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody

class LoginRepository(private val apiService: LoginApiService) {

    fun login(
        email: String,
        password: String,
        loginError: MutableLiveData<Exception>,
        loginResponse: MutableLiveData<ResponseBody>
    ) {
        runBlocking {
            delay(1000L)
            try {
                loginResponse.value = apiService.login(User(email, password))
            } catch (e: Exception) {
                loginError.value = e
            }
        }
    }
}

package com.example.catapp.api

import com.example.catapp.model.User
import com.example.catapp.utils.Constants
import okhttp3.ResponseBody

class LoginApiService {

    fun login(user: User): ResponseBody {
        if (!Constants.users.contains(user)) {
            throw Exception("This catstomer does not exist! Please try one of our designated users!")
        } else {
            return ResponseBody.create(null, "Hello")
        }
    }
}
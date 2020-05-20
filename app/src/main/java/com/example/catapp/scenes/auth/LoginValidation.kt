package com.example.catapp.scenes.auth

import com.example.catapp.utils.Constants.Companion.characterEmail
import com.example.catapp.utils.Constants.Companion.passwordMinimLength

object LoginValidation {

    private fun validateEmail(email: String?) = !email.isNullOrEmpty() && email.contains(
        characterEmail
    )

    private fun validatePassword(password: String?) =
        !password.isNullOrEmpty() && password.length >= passwordMinimLength

    fun validateFields(email: String?, password: String?) =
        validateEmail(email) && validatePassword(password)
}
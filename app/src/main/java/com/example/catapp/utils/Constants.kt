package com.example.catapp.utils

import com.example.catapp.model.User

class Constants {

    companion object {
        const val paginationLimit = 10
        const val passwordMinimLength = 6
        const val characterEmail = "@"

        val users = mutableListOf(
            User("mara@email.com", "qwerty"),
            User("lola@email.com", "qwerty"),
            User("prislea@email.com", "qwerty"),
            User("puppy@email.com", "qwerty"),
            User("tomi@email.com", "qwerty")
        )
    }
}
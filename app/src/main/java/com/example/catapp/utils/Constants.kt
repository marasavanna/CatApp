package com.example.catapp.utils

import com.example.catapp.model.User

class Constants {

    companion object {
        const val paginationLimit = 10

        val users = mutableListOf(
            User("ana", "sjsj"),
            User("mama", "kiki")
        )
    }
}
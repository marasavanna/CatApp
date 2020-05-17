package com.example.catapp.model

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BreedDataItem(
    val id: String,
    val origin: String,
    val name: String,
    val temperament: String,
    val country_code: String,
    val wikipedia_url: String?,
    val description: String
)
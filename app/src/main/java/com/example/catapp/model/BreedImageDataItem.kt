package com.example.catapp.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedImageDataItem(val url: String)
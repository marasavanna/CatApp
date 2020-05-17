package com.example.catapp.scenes.breed_details

data class BreedDetailsWrapper(
    val image: String,
    val name: String,
    val description: String,
    val countryCode: String,
    val temperament: String,
    val wikiLink: String
)
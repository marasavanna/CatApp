package com.example.catapp.scenes.breed_details

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BreedDetailsWrapper(
    val image: String,
    val name: String,
    val description: String,
    val countryCode: String,
    val temperament: String,
    val wikiLink: String
) : Parcelable
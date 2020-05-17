package com.example.catapp.api

import com.example.catapp.model.BreedDataItem
import com.example.catapp.model.BreedImageDataItem
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatBreedApiService {

    @GET("breeds")
    fun getCatBreedsAsync(): Deferred<MutableList<BreedDataItem>>

    @GET("images/search")
    fun getCatBreedImageAsync(@Query("breed_id") breedId: String): Deferred<MutableList<BreedImageDataItem>>
}
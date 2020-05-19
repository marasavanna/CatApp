package com.example.catapp.api

import com.example.catapp.model.BreedDataItem
import com.example.catapp.model.BreedImageDataItem
import com.example.catapp.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface CatBreedApiService {

    @GET("breeds")
    fun getCatBreedsAsync(@Query("page") pageIndex: Int,
                          @Query("limit") limit: Int): Deferred<MutableList<BreedDataItem>>

    @GET("breeds/search")
    fun findByBreedNameAsync(@Query("q") breedName: String): Deferred<MutableList<BreedDataItem>>

    @GET("images/search")
    fun getCatBreedImageAsync(
        @Query("breed_id") breedId: String
    ): Deferred<MutableList<BreedImageDataItem>>

}
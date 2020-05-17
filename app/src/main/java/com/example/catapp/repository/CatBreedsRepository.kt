package com.example.catapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.CatBreedApiService
import com.example.catapp.model.BreedDataItem
import com.example.catapp.model.BreedImageDataItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CatBreedsRepository(private val catBreedApiService: CatBreedApiService) {

    fun getCatBreeds(
        catBreeds: MutableLiveData<MutableList<BreedDataItem>>,
        catBreedsFetchError: MutableLiveData<Exception>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = catBreedApiService.getCatBreedsAsync()
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    catBreeds.value = response
                } catch (e: Exception) {
                    catBreedsFetchError.value = e
                }
            }
        }
    }

    fun getCardBreedImage(
        breedId: String,
        breedImage: MutableLiveData<MutableList<BreedImageDataItem>>,
        imageFetchError: MutableLiveData<Exception>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = catBreedApiService.getCatBreedImageAsync(breedId)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    breedImage.value = response
                } catch (e: Exception) {
                    imageFetchError.value = e
                }
            }
        }
    }
}
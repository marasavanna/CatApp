package com.example.catapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.CatBreedApiService
import com.example.catapp.model.BreedDataItem
import com.example.catapp.model.BreedImageDataItem
import com.example.catapp.utils.Constants.Companion.paginationLimit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CatBreedsRepository(private val catBreedApiService: CatBreedApiService) {

    fun getCatBreeds(
        catBreeds: MutableLiveData<MutableList<BreedDataItem>>,
        catBreedsFetchError: MutableLiveData<Exception>,
        isLoading: MutableLiveData<Boolean>,
        page: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = catBreedApiService.getCatBreedsAsync(page, paginationLimit)
            isLoading.postValue(true)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    catBreeds.value = response
                } catch (e: Exception) {
                    catBreedsFetchError.value = e
                    isLoading.postValue( false)
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
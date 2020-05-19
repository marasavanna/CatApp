package com.example.catapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.CatBreedApiService
import com.example.catapp.model.BreedImageDataItem
import com.example.catapp.scenes.cat_breeds.CatBreedItemWrapper
import com.example.catapp.utils.Constants.Companion.paginationLimit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CatBreedsRepository(private val catBreedApiService: CatBreedApiService) {

    fun getCatBreeds(
        catBreeds: MutableLiveData<MutableList<CatBreedItemWrapper>>,
        catBreedsFetchError: MutableLiveData<Exception>,
        isLoading: MutableLiveData<Boolean>,
        page: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = catBreedApiService.getCatBreedsAsync(page, paginationLimit)
            isLoading.postValue(true)
            withContext(Dispatchers.Main) {
                try {
                    val catBreedsResults = mutableListOf<CatBreedItemWrapper>()
                    val response = request.await()
                    response.map { breedItem ->
                        catBreedsResults.add(
                            CatBreedItemWrapper(
                                "https://cdn2.thecatapi.com/images/tv8tNeYaU.jpg",
                                breedItem.name,
                                breedItem.description
                            )
                        )

                    }
                    catBreeds.value = catBreedsResults
                } catch (e: Exception) {
                    catBreedsFetchError.value = e
                    isLoading.postValue(false)
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
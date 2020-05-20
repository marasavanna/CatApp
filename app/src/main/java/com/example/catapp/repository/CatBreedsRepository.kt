package com.example.catapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.CatBreedApiService
import com.example.catapp.scenes.breed_details.BreedDetailsWrapper
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
        page: Int?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = if (page != null) {
                catBreedApiService.getCatBreedsAsync(page, paginationLimit)
            } else {
                catBreedApiService.getCatBreedsAsync(null, null)
            }

            withContext(Dispatchers.Main) {
                try {
                    val catBreedsResults = mutableListOf<CatBreedItemWrapper>()
                    val response = request.await()
                    response.map { breedItem ->
                        val imageRequest = withContext(Dispatchers.IO) {
                            catBreedApiService.getCatBreedImageAsync(breedItem.id)
                        }
                        catBreedsResults.add(
                            CatBreedItemWrapper(
                                imageRequest.await().first().url,
                                breedItem.name,
                                breedItem.description,
                                breedItem.origin
                            )
                        )
                    }
                    catBreedsResults.sortBy { catBreedItemWrapper -> catBreedItemWrapper.name }
                    catBreeds.value = catBreedsResults
                } catch (e: Exception) {
                    catBreedsFetchError.value = e
                }
            }
        }
    }

    fun findCatBreedByName(
        breedName: String,
        breedDescription: String,
        breedDetail: MutableLiveData<BreedDetailsWrapper>,
        catBreedFetchError: MutableLiveData<Exception>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = catBreedApiService.findByBreedNameAsync(breedName)
            withContext(Dispatchers.Main) {
                try {
                    val response = request.await()
                    val singledResponse =
                        response.findLast { breedDataItem -> breedDataItem.description == breedDescription }
                    singledResponse?.let {
                        val imageRequest = withContext(Dispatchers.IO) {
                            catBreedApiService.getCatBreedImageAsync(it.id)
                        }
                        breedDetail.value = singledResponse.wikipedia_url?.let { wikiLink ->
                            BreedDetailsWrapper(
                                imageRequest.await().first().url,
                                singledResponse.name,
                                singledResponse.description,
                                singledResponse.country_code,
                                singledResponse.temperament,
                                wikiLink
                            )
                        }
                    }

                } catch (e: Exception) {
                    catBreedFetchError.value = e
                }
            }
        }
    }
}
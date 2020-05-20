package com.example.catapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.catapp.api.CatBreedApiService
import com.example.catapp.scenes.breed_details.BreedDetailsWrapper
import com.example.catapp.scenes.cat_breeds.CatBreedItemWrapper
import com.example.catapp.utils.Constants.Companion.imageLimit
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow


class CatBreedsRepository(private val catBreedApiService: CatBreedApiService) {

    @FlowPreview
    fun getCatBreeds(
        catBreeds: MutableLiveData<MutableList<CatBreedItemWrapper>>,
        catBreedsFetchError: MutableLiveData<Exception>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val request = catBreedApiService.getCatBreedsAsync()
            withContext(Dispatchers.Main) {
                try {
                    val catBreedsResults = mutableListOf<CatBreedItemWrapper>()
                    val response = request.await()
                    response.asFlow().flatMapMerge(concurrency = 8) { catBreedItem ->
                        flow {
                            emit(
                                catBreedItem to catBreedApiService.getCatBreedImageAsync(
                                    catBreedItem.id,
                                    imageLimit
                                )
                            )
                        }
                    }.collect { pair ->
                        catBreedsResults.add(
                            CatBreedItemWrapper(
                                pair.second.await().first().url,
                                pair.first.name,
                                pair.first.description,
                                pair.first.origin
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
                            catBreedApiService.getCatBreedImageAsync(it.id, imageLimit)
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
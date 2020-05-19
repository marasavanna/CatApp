package com.example.catapp.scenes.cat_breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.bases.BaseViewModel
import com.example.catapp.model.BreedDataItem
import com.example.catapp.model.BreedImageDataItem
import com.example.catapp.repository.CatBreedsRepository

class CatBreedsViewModel(private val catBreedsRepository: CatBreedsRepository) : BaseViewModel() {

    val catBreeds: LiveData<MutableList<CatBreedItemWrapper>>
        get() = _catBreeds
    private val _catBreeds = MutableLiveData<MutableList<CatBreedItemWrapper>>()

    val catBreedsFetchError: LiveData<Exception>
        get() = _catBreedsFetchError
    private val _catBreedsFetchError = MutableLiveData<Exception>()

    val catBreedImages: LiveData<MutableList<BreedImageDataItem>>
        get() = _catBreedImages
    private val _catBreedImages = MutableLiveData<MutableList<BreedImageDataItem>>()

    val catBreedImageFetchError: LiveData<Exception>
        get() = _catBreedImageFetchError
    private val _catBreedImageFetchError = MutableLiveData<Exception>()

    val isLoading = MutableLiveData<Boolean>()


    fun getCatBreeds(page: Int) {
        catBreedsRepository.getCatBreeds(_catBreeds, _catBreedsFetchError, isLoading, page)
    }

    fun getCatImage(breedId: String) {
        catBreedsRepository.getCardBreedImage(breedId, _catBreedImages, _catBreedImageFetchError)
    }
}
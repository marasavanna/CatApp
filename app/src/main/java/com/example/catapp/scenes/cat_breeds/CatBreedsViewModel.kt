package com.example.catapp.scenes.cat_breeds

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.bases.BaseViewModel
import com.example.catapp.model.BreedImageDataItem
import com.example.catapp.repository.CatBreedsRepository
import com.example.catapp.scenes.breed_details.BreedDetailsWrapper

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


    fun getCatBreeds(page: Int) {
        catBreedsRepository.getCatBreeds(
            _catBreeds,
            _catBreedsFetchError,
            page
        )
    }

    fun filterBreedsByCountry(origin: String, initialBreeds: List<CatBreedItemWrapper>): List<CatBreedItemWrapper> {
        return initialBreeds.filter { catBreedItemWrapper -> catBreedItemWrapper.origin == origin }
    }

    fun getCatImage(breedId: String) {
        catBreedsRepository.getCardBreedImage(breedId, _catBreedImages, _catBreedImageFetchError)
    }
}
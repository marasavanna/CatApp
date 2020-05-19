package com.example.catapp.scenes.cat_breeds

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

    val catBreedsDetails: LiveData<MutableList<BreedDetailsWrapper>>
        get() = _catBreedsDetails
    private val _catBreedsDetails = MutableLiveData<MutableList<BreedDetailsWrapper>>()

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


    fun findDetailsWrapper(name: String): BreedDetailsWrapper? {
        return _catBreedsDetails.value?.firstOrNull { breedDetailsWrapper -> breedDetailsWrapper.name == name }
    }

    fun getCatBreeds(page: Int) {
        catBreedsRepository.getCatBreeds(
            _catBreeds,
            _catBreedsDetails,
            _catBreedsFetchError,
            isLoading,
            page
        )
    }

    fun getCatImage(breedId: String) {
        catBreedsRepository.getCardBreedImage(breedId, _catBreedImages, _catBreedImageFetchError)
    }
}
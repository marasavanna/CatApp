package com.example.catapp.scenes.cat_breeds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.bases.BaseViewModel
import com.example.catapp.model.BreedImageDataItem
import com.example.catapp.repository.CatBreedsRepository
import java.util.*

class CatBreedsViewModel(private val catBreedsRepository: CatBreedsRepository) : BaseViewModel() {

    val catBreeds: LiveData<MutableList<CatBreedItemWrapper>>
        get() = _catBreeds
    private val _catBreeds = MutableLiveData<MutableList<CatBreedItemWrapper>>()

    val filteredCats: LiveData<List<CatBreedItemWrapper>>
        get() = _filteredCats
    private val _filteredCats = MutableLiveData<List<CatBreedItemWrapper>>()

    val catBreedsFetchError: LiveData<Exception>
        get() = _catBreedsFetchError
    private val _catBreedsFetchError = MutableLiveData<Exception>()

    fun getCatBreeds(page: Int?) {
        catBreedsRepository.getCatBreeds(
            _catBreeds,
            _catBreedsFetchError,
            page
        )
    }

    fun filterBreedsByCountry(
        origin: String,
        initialBreeds: List<CatBreedItemWrapper>
    ) {
        _filteredCats.value = initialBreeds.filter { catBreedItemWrapper ->
            catBreedItemWrapper.origin.toLowerCase(Locale.ENGLISH)
                .contains(origin.toLowerCase(Locale.ENGLISH))
        }
    }
}
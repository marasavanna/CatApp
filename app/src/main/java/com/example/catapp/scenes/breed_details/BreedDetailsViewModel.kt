package com.example.catapp.breed_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.catapp.bases.BaseViewModel
import com.example.catapp.repository.CatBreedsRepository
import com.example.catapp.scenes.breed_details.BreedDetailsWrapper

class BreedDetailsViewModel(private val repository: CatBreedsRepository) : BaseViewModel() {

    val catBreedDetail: LiveData<BreedDetailsWrapper>
        get() = _catBreedDetail
    private val _catBreedDetail = MutableLiveData<BreedDetailsWrapper>()

    val catBreedFetchError: LiveData<Exception>
        get() = _catBreedsFetchError
    private val _catBreedsFetchError = MutableLiveData<Exception>()

    fun findDetailsByName(name: String, description: String) {
        repository.findCatBreedByName(name, description, _catBreedDetail, _catBreedsFetchError)
    }
}
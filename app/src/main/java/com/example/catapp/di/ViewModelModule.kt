package com.example.catapp.di

import com.example.catapp.api.CatBreedApiService
import com.example.catapp.breed_details.BreedDetailsViewModel
import com.example.catapp.repository.CatBreedsRepository
import com.example.catapp.scenes.cat_breeds.CatBreedsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    single {
        provideCatBreedsRepository(get())
    }

    viewModel { CatBreedsViewModel(get()) }
    viewModel { BreedDetailsViewModel(get()) }
}

fun provideCatBreedsRepository(
    restApiServiceService: CatBreedApiService
): CatBreedsRepository = CatBreedsRepository(restApiServiceService)
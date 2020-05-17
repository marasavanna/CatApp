package com.example.catapp.scenes.cat_breeds

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.R
import com.example.catapp.bases.BaseViewModelFragment
import com.example.catapp.databinding.FragmentCatBreedsBinding
import com.example.catapp.utils.ToolbarFragment
import com.example.catapp.utils.navigateIfAdded
import com.example.catapp.utils.observeNonNull
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatBreedsFragment : BaseViewModelFragment<FragmentCatBreedsBinding, CatBreedsViewModel>() {

    override val viewModel: CatBreedsViewModel by viewModel()
    override fun builder(): ToolbarFragment? = null

    override val layoutRes: Int
        get() = R.layout.fragment_cat_breeds


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getCatBreeds()

        viewModel.getCatImage("abys")

        viewModel.catBreedImages.observeNonNull(this) {
            val catBreed = CatBreedItemWrapper(
                it[0].url,
                "Abyssinian",
                "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals."
            )

            val adapter = CatBreedsAdapter()
            val catBreeds = mutableListOf<CatBreedItemWrapper>()
            catBreeds.add(catBreed)
            catBreeds.add(catBreed)
            catBreeds.add(catBreed)
            catBreeds.add(catBreed)
            catBreeds.add(catBreed)
            catBreeds.add(catBreed)

            adapter.apply {
                this.catBreeds = catBreeds
                setOnCatItemClickListener {
                    findNavController().navigateIfAdded(
                        this@CatBreedsFragment,
                        CatBreedsFragmentDirections.breedsToDetails(),
                        R.id.catBreedsFragment
                    )
                }
            }
            binding.catBreeds.adapter = adapter
        }

        viewModel.catBreeds.observeNonNull(viewLifecycleOwner) {
            println("para $it")
        }

        binding.catBreeds.layoutManager = LinearLayoutManager(requireContext())

    }
}
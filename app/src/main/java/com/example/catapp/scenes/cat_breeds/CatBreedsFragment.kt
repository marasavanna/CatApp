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

    private val adapter = CatBreedsAdapter()
    private var page = 1

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getCatBreeds(page)

        viewModel.getCatImage("abys")


        binding.catBreeds.layoutManager = LinearLayoutManager(requireContext())
        binding.catBreeds.adapter = adapter
        binding.catBreeds.addOnScrollListener(object :
            PaginationScrollListener(
                binding.catBreeds.layoutManager as LinearLayoutManager
            ) {
            override fun loadMoreItems() {
                shouldLoadMore = false
                page++
                viewModel.getCatBreeds(page)
//                shouldLoadMore = true
            }

        })

        adapter.setOnCatItemClickListener {
            findNavController().navigateIfAdded(
                this@CatBreedsFragment,
                CatBreedsFragmentDirections.breedsToDetails(),
                R.id.catBreedsFragment
            )
        }

        viewModel.catBreeds.observeNonNull(viewLifecycleOwner) {
            val catBreeds = mutableListOf<CatBreedItemWrapper>()
            it.map { breedDataItem ->
                catBreeds.add(
                    CatBreedItemWrapper(
                        "https://cdn2.thecatapi.com/images/KWdLHmOqc.jpg",
                        breedDataItem.name, breedDataItem.description
                    )
                )
            }
            adapter.notifyChanges(catBreeds)
        }

    }
}
package com.example.catapp.scenes.cat_breeds

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.catapp.R
import com.example.catapp.bases.BaseViewModelFragment
import com.example.catapp.databinding.FragmentCatBreedsBinding
import com.example.catapp.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PagedCatBreedsFragment :
    BaseViewModelFragment<FragmentCatBreedsBinding, CatBreedsViewModel>() {

    override val viewModel: CatBreedsViewModel by viewModel()
    override fun builder(): ToolbarFragment? = null

    override val layoutRes: Int
        get() = R.layout.fragment_cat_breeds

    private val adapter = CatBreedsAdapter()
    private var page = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val scrollListener = object : PaginationScrollListener(
            binding.catBreeds.layoutManager as LinearLayoutManager
        ) {
            override fun loadMoreItems() {
                shouldLoadMore = false
                startLoading()
                viewModel.getCatBreeds(++page)
            }
        }
        binding.catBreeds.addOnScrollListener(scrollListener)

        viewModel.filteredCats.observeNonNull(viewLifecycleOwner) {
            scrollListener.shouldLoadMore = false
            adapter.replaceItems(it as MutableList<CatBreedItemWrapper>)
        }

        viewModel.catBreeds.observeNonNull(viewLifecycleOwner) {
            scrollListener.shouldLoadMore = true
            stopLoading()
            adapter.notifyChanges(it)

            binding.searchByCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.isNullOrEmpty()) {
                        viewModel.catBreeds.value?.let { catBreeds ->
                            adapter.replaceItems(catBreeds.toMutableList())
                        }
                    } else {
                        viewModel.filterBreedsByCountry(
                            query,
                            it
                        )
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        scrollListener.shouldLoadMore = true
                        viewModel.catBreeds.value?.let { catBreeds ->
                            adapter.replaceItems(catBreeds.toMutableList())
                        }
                    } else {
                        viewModel.filterBreedsByCountry(
                            newText,
                            it
                        )
                    }
                    return false
                }
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        startLoading()

        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.getCatBreeds(page)
        }

        binding.catBreeds.layoutManager = LinearLayoutManager(requireContext())
        binding.catBreeds.adapter = adapter

        adapter.setOnCatItemClickListener {
            val directions =
                PagedCatBreedsFragmentDirections.breedsToDetails(it.name, it.description)
            findNavController().navigateIfAdded(
                this@PagedCatBreedsFragment,
                directions,
                R.id.pagedCatBreedsFragment
            )
        }

        viewModel.catBreedsFetchError.observeNonNull(viewLifecycleOwner) {
            stopLoading()
            requireContext().displayModalPopup(
                getString(R.string.something_bad_happened),
                it.message
            )
        }
    }
}
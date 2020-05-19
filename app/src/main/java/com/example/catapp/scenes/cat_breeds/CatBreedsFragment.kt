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
    private var page = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val scrollListener = object : PaginationScrollListener(
            binding.catBreeds.layoutManager as LinearLayoutManager
        ) {
            override fun loadMoreItems() {
                shouldLoadMore = false
                viewModel.isLoading.set(true)
                viewModel.getCatBreeds(++page)
            }
        }
        binding.catBreeds.addOnScrollListener(scrollListener)

        viewModel.catBreeds.observeNonNull(viewLifecycleOwner) {
            scrollListener.shouldLoadMore = true
            viewModel.isLoading.set(false)
            adapter.notifyChanges(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.searchByCountry.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//               query?.let {
//
//               }
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//
//            }
//
//        })

        viewModel.getCatBreeds(page)

        binding.catBreeds.layoutManager = LinearLayoutManager(requireContext())
        binding.catBreeds.adapter = adapter

        binding.viewModel = viewModel

        adapter.setOnCatItemClickListener {
            val directions =
                CatBreedsFragmentDirections.breedsToDetails(it.name, it.description)
            findNavController().navigateIfAdded(
                this@CatBreedsFragment,
                directions,
                R.id.catBreedsFragment
            )
        }
    }
}
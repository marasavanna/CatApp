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

class CatBreedsFragment : BaseViewModelFragment<FragmentCatBreedsBinding, CatBreedsViewModel>() {
    override val viewModel: CatBreedsViewModel by viewModel()
    override val layoutRes: Int
        get() = R.layout.fragment_cat_breeds

    override fun builder(): ToolbarFragment? = null

    private val adapter = CatBreedsAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        startLoading()

        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.getCatBreeds()
        }

        binding.catBreeds.layoutManager = LinearLayoutManager(requireContext())
        binding.catBreeds.adapter = adapter

        adapter.setOnCatItemClickListener {
            val directions =
                CatBreedsFragmentDirections.breedsToDetails(it.name, it.description)
            findNavController().navigateIfAdded(
                this@CatBreedsFragment,
                directions,
                R.id.catBreedsFragment
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.filteredCats.observeNonNull(viewLifecycleOwner) {
            adapter.replaceItems(it as MutableList<CatBreedItemWrapper>)
        }

        viewModel.catBreeds.observeNonNull(this) {
            stopLoading()
            adapter.notifyChanges(it)

            binding.searchByCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
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
}

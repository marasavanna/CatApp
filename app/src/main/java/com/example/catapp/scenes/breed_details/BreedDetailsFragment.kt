package com.example.catapp.breed_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.catapp.R
import com.example.catapp.bases.BaseViewModelFragment
import com.example.catapp.databinding.FragmentBreedDetailsBinding
import com.example.catapp.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BreedDetailsFragment :
    BaseViewModelFragment<FragmentBreedDetailsBinding, BreedDetailsViewModel>() {
    override val viewModel: BreedDetailsViewModel by viewModel()

    override fun builder(): ToolbarFragment {
        return ToolbarFragment.Builder()
            .with(binding.toolbar)
            .shouldDisplayBack(requireActivity())
            .build()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_breed_details

    private val args by navArgs<BreedDetailsFragmentArgs>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.catBreedDetail.observeNonNull(viewLifecycleOwner) {
            viewModel.isLoading.set(false)
            val directions =
                viewModel.catBreedDetail.value?.wikiLink?.let { link ->
                    BreedDetailsFragmentDirections.detailsToWiki(
                        link
                    )
                }
            binding.wikiLink.setOnClickListener {
                directions?.let { directions ->
                    findNavController().navigateIfAdded(
                        this@BreedDetailsFragment,
                        directions,
                        R.id.breedDetailsFragment
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        if (NetworkUtils.isNetworkAvailable(requireContext())) {
            viewModel.findDetailsByName(args.breedName, args.breedDescription)
        }
        viewModel.catBreedFetchError.observeNonNull(viewLifecycleOwner) {
            viewModel.isLoading.set(false)
            requireContext().displayModalPopup(
                getString(R.string.something_bad_happened),
                it.message
            )
        }
    }
}
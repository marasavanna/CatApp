package com.example.catapp.breed_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController

import com.example.catapp.R
import com.example.catapp.bases.BaseFragment
import com.example.catapp.databinding.FragmentBreedDetailsBinding
import com.example.catapp.utils.ToolbarFragment
import com.example.catapp.utils.navigateIfAdded

class BreedDetailsFragment : BaseFragment<FragmentBreedDetailsBinding>() {
    override fun builder(): ToolbarFragment {
        return ToolbarFragment.Builder()
            .with(binding.toolbar)
            .shouldDisplayBack(requireActivity())
            .build()
    }

    override val layoutRes: Int
        get() = R.layout.fragment_breed_details

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val breedDetail = arguments?.let { BreedDetailsFragmentArgs.fromBundle(it).breedDetail }

        breedDetail?.let {
            binding.breedDetail = it
            val directions =
                BreedDetailsFragmentDirections.detailsToWiki(breedDetail.wikiLink)
            binding.wikiLink.setOnClickListener {
                findNavController().navigateIfAdded(
                    this@BreedDetailsFragment,
                    directions,
                    R.id.breedDetailsFragment
                )
            }
        }
    }
}
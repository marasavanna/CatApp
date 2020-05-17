package com.example.catapp.breed_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.catapp.R
import com.example.catapp.bases.BaseFragment
import com.example.catapp.databinding.FragmentBreedDetailsBinding
import com.example.catapp.scenes.breed_details.BreedDetailsWrapper
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

        val breedDetail = BreedDetailsWrapper(
            "https://cdn2.thecatapi.com/images/MTkyMzUxMA.jpg",
            "Abyssinian",
            "The Abyssinian is easy to care for, and a joy to have in your home. They’re affectionate cats and love both people and other animals.",
            "EG",
            "Active, Energetic, Independent, Intelligent, Gentle",
            "https://en.wikipedia.org/wiki/Abyssinian_(cat)"
        )

        binding.breedDetail = breedDetail
        binding.wikiLink.setOnClickListener {
            val directions = BreedDetailsFragmentDirections.detailsToWiki(breedDetail.wikiLink)
            findNavController().navigateIfAdded(
                this@BreedDetailsFragment,
                directions,
                R.id.breedDetailsFragment
            )
        }
    }
}
package com.example.catapp.scenes.cat_breeds

import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.databinding.ItemCatBreedBinding

class CatBreedViewHolder(
    private val binding: ItemCatBreedBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CatBreedItemWrapper, onCatItemClickListener: OnCatItemClickListener) {
        binding.breedItem = item
        binding.root.setOnClickListener { onCatItemClickListener.invoke(item) }
    }
}
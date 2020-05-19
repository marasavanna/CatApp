package com.example.catapp.scenes.cat_breeds

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.catapp.R

typealias OnCatItemClickListener = (CatBreedItemWrapper) -> Unit

class CatBreedsAdapter : RecyclerView.Adapter<CatBreedViewHolder>() {

    var catBreeds = mutableListOf<CatBreedItemWrapper>()

    private var onCatItemClickListener: OnCatItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CatBreedViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cat_breed,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = catBreeds.size

    override fun onBindViewHolder(holder: CatBreedViewHolder, position: Int) {
        onCatItemClickListener?.let {
            holder.bind(catBreeds[position], it)
        }
    }

    fun setOnCatItemClickListener(onCatItemClickListener: OnCatItemClickListener) {
        this.onCatItemClickListener = onCatItemClickListener
    }

    fun notifyChanges(newList: MutableList<CatBreedItemWrapper>) {
//        val diffCallback = CatBreedsDiffUtil(catBreeds, newList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        diffResult.dispatchUpdatesTo(this)
        catBreeds.addAll(newList)
        notifyDataSetChanged()
    }

    fun replaceItems(newList: MutableList<CatBreedItemWrapper>) {
//        val diffCallback = CatBreedsDiffUtil(catBreeds, newList)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
//        diffResult.dispatchUpdatesTo(this)
        catBreeds.clear()
        catBreeds.addAll(newList)
        notifyDataSetChanged()
    }
}
package com.example.catapp.scenes.cat_breeds

import androidx.recyclerview.widget.DiffUtil

class CatBreedsDiffUtil(private val oldList: List<CatBreedItemWrapper>, private val newList: List<CatBreedItemWrapper>): DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val currentOldItem = oldList[oldItemPosition]
        val currentNewItem = newList[newItemPosition]

        return  currentNewItem.name == currentOldItem.name

    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]


    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size
}
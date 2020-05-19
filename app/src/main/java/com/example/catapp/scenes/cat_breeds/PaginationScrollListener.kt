package com.example.catapp.scenes.cat_breeds

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationScrollListener(
    private val layoutManager: LinearLayoutManager
) :
    RecyclerView.OnScrollListener() {
    var shouldLoadMore: Boolean = true

    protected abstract fun loadMoreItems()

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (shouldLoadMore && visibleItemCount + firstVisibleItemPosition >= totalItemCount){
            loadMoreItems()
        }
    }


}
package com.example.catapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
}
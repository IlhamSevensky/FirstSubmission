package com.app.core.extensions

import android.view.View
import android.widget.ImageView
import coil.clear
import coil.load
import coil.transform.RoundedCornersTransformation
import com.app.core.R

fun View.visibleOrGone(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
}

fun ImageView.loadUrl(url: String) {
    this.clear()
    this.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_image_loading)
        error(R.drawable.ic_no_image_available)
    }
}

fun ImageView.loadUrlRounded(url: String, radius: Float = 8F) {
    this.clear()
    this.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_image_loading)
        error(R.drawable.ic_no_image_available)
        transformations(RoundedCornersTransformation(radius))
    }
}
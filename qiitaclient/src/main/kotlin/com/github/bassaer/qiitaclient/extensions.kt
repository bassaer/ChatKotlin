package com.github.bassaer.qiitaclient

import android.content.Context
import android.databinding.BindingAdapter
import android.webkit.WebView
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide

/**
 * Extensions
 * Created by nakayama on 2017/09/10.
 */
fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

@BindingAdapter("bind:imageUrl")
fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).into(this)
}

@BindingAdapter("bind:loadUrl")
fun WebView.loadUrl(url: String) {
    loadUrl(url)
}
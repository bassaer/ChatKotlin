package com.github.bassaer.qiitaclient

import android.support.annotation.IdRes
import android.view.View

/**
 * Extensions
 * Created by nakayama on 2017/09/10.
 */
fun <T : View> View.bindView(@IdRes id: Int): Lazy<T> = lazy {
    findViewById(id) as T
}
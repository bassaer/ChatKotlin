package com.github.bassaer.qiitaclient.view

import android.content.Context
import android.databinding.BindingMethod
import android.databinding.BindingMethods
import android.databinding.DataBindingUtil
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.github.bassaer.qiitaclient.R
import com.github.bassaer.qiitaclient.databinding.ViewArticleBinding
import com.github.bassaer.qiitaclient.model.Article

/**
 * Created by nakayama on 2017/09/10.
 */
@BindingMethods(BindingMethod(
        type = Article::class,attribute = "bind:article", method = "setArticle"))
class ArticleView : FrameLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    val binding: ViewArticleBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.view_article,
                this,
                true
    )

    fun setArticle(article: Article) {
        binding.article = article
    }
}
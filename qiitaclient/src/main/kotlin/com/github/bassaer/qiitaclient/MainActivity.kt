package com.github.bassaer.qiitaclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.bassaer.qiitaclient.model.ArticleClient
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.setContentView
import javax.inject.Inject

class MainActivity : RxAppCompatActivity() {

    @Inject
    lateinit var articleClient: ArticleClient

    val ui: MainActivityUI by lazy {
        MainActivityUI().apply { setContentView(this@MainActivity) }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as QiitaClientApp).component.inject(this)

        val listAdapter = ArticleListAdapter(applicationContext)

        ui.listView.adapter = listAdapter
        ui.listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intent(this, article).let { startActivity(it) }
        }

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        ui.searchButton.setOnClickListener {
            ui.progressBar.visibility = View.VISIBLE
            inputMethodManager.hideSoftInputFromWindow(
                    ui.mainLayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
            ui.mainLayout.requestFocus()
            articleClient.search(ui.queryEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate {
                        ui.progressBar.visibility = View.GONE
                    }
                    .bindToLifecycle(this)
                    .subscribe ({
                        ui.queryEditText.text.clear()
                        listAdapter.articles = it
                        listAdapter.notifyDataSetChanged()
                    }, {
                      toast("Error : $it")
                    })
        }

    }
}

package com.github.bassaer.qiitaclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.github.bassaer.qiitaclient.model.ArticleClient
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : RxAppCompatActivity() {

    @Inject
    lateinit var articleClient: ArticleClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as QiitaClientApp).component.inject(this)
        setContentView(R.layout.activity_main)

        val queryEditText = findViewById<EditText>(R.id.query_edit_text)
        val searchButton = findViewById<Button>(R.id.search_button)
        val progressBar = findViewById<ProgressBar>(R.id.progress_bar)
        val listView = findViewById<ListView>(R.id.list_view)
        val mainLayout = findViewById<LinearLayout>(R.id.main_layout)

        val listAdapter = ArticleListAdapter(applicationContext)

        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intnet(this, article).let { startActivity(it) }
        }

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        searchButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            inputMethodManager.hideSoftInputFromWindow(
                    mainLayout.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
            mainLayout.requestFocus()
            articleClient.search(queryEditText.text.toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doAfterTerminate {
                        progressBar.visibility = View.GONE
                    }
                    .bindToLifecycle(this)
                    .subscribe ({
                        queryEditText.text.clear()
                        listAdapter.articles = it
                        listAdapter.notifyDataSetChanged()
                    }, {
                      toast("Error : $it")
                    })
        }

    }
}

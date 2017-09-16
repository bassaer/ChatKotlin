package com.github.bassaer.qiitaclient

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.ProgressBar
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

        val listAdapter = ArticleListAdapter(applicationContext)

        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intnet(this, article).let { startActivity(it) }
        }



        searchButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
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

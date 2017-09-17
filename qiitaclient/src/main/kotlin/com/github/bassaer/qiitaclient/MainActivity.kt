package com.github.bassaer.qiitaclient

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import butterknife.bindView
import com.github.bassaer.qiitaclient.model.ArticleClient
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivity : RxAppCompatActivity() {

    @Inject
    lateinit var articleClient: ArticleClient

    val listView: ListView by bindView(R.id.list_view)
    val queryEditText: EditText by bindView(R.id.query_edit_text)
    val progressBar: ProgressBar by bindView(R.id.progress_bar)
    val searchButton: Button by bindView(R.id.search_button)
    val mainLayout: LinearLayout by bindView(R.id.main_layout)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as QiitaClientApp).component.inject(this)
        setContentView(R.layout.activity_main)

        val listAdapter = ArticleListAdapter(applicationContext)

        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intent(this, article).let { startActivity(it) }
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

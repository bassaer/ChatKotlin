package com.github.bassaer.qiitaclient

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView
import com.github.bassaer.qiitaclient.model.Article
import com.github.bassaer.qiitaclient.model.User

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listAdapter = ArticleListAdapter(applicationContext)
        listAdapter.articles = listOf(
                createArticle("Kotlin Book", "Kotlin"),
                createArticle("Java Book", "Java"))
        val listView: ListView = findViewById(R.id.list_view) as ListView
        listView.adapter = listAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val article = listAdapter.articles[position]
            ArticleActivity.intnet(this, article).let { startActivity(it) }
        }

    }

    private fun createArticle(title: String, username: String) : Article =
            Article(id = "",
                    title = title,
                    url = "https://kotlinlang.org/",
                    user = User(id = "", name = username, profileImageUrl = ""))
}

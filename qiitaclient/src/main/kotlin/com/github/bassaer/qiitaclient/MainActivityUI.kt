package com.github.bassaer.qiitaclient

import android.view.Gravity
import android.view.View
import android.widget.*
import org.jetbrains.anko.*
import kotlin.properties.Delegates

/**
 * Created by nakayama on 2017/09/17.
 */
class MainActivityUI : AnkoComponent<MainActivity> {

    var listView: ListView by Delegates.notNull()
        private set

    var progressBar: ProgressBar by Delegates.notNull()
        private set

    var searchButton: Button by Delegates.notNull()
        private set

    var queryEditText: EditText by Delegates.notNull()
        private set

    var mainLayout: LinearLayout by Delegates.notNull()
        private set


    override fun createView(ui: AnkoContext<MainActivity>): View = ui.run {
        verticalLayout {
            mainLayout = this
            frameLayout {
                gravity = Gravity.CENTER
                listView = listView().lparams(matchParent, matchParent)
                progressBar = progressBar {
                    visibility = View.GONE
                }.lparams(wrapContent, wrapContent, gravity = Gravity.CENTER)
            }.lparams(width = matchParent, height = 0, weight = 1f)

            linearLayout {
                queryEditText = editText().lparams(width = 0, weight = 1f)
                searchButton = button(R.string.search)
            }.lparams(width = matchParent) {
                gravity = Gravity.BOTTOM
            }
        }
    }
}
package com.github.bassaer.qiitaclient

import android.app.Application
import com.github.bassaer.qiitaclient.dagger.AppComponent
import com.github.bassaer.qiitaclient.dagger.DaggerAppComponent

/**
 * Created by nakayama on 2017/09/16.
 */
class QiitaClientApp : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.create()
    }
}
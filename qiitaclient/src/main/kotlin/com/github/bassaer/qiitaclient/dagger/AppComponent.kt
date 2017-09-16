package com.github.bassaer.qiitaclient.dagger

import com.github.bassaer.qiitaclient.MainActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by nakayama on 2017/09/16.
 */
@Component(modules = arrayOf(ClientModule::class))
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}
package com.fuzzyfunctors.reductions.store

import android.app.Application
import com.fuzzyfunctors.reductions.flipper.FlipperImpl

class StoreApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FlipperImpl().initialize(this)
    }
}

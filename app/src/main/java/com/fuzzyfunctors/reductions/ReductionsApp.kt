package com.fuzzyfunctors.reductions

import android.app.Application
import com.fuzzyfunctors.reductions.injection.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReductionsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ReductionsApp)
            modules(Modules.get(false))
        }
    }
}

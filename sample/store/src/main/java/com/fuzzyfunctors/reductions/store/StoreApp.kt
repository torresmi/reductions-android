package com.fuzzyfunctors.reductions.store

import android.app.Application
import com.fuzzyfunctors.reductions.flipper.api.Flipper
import com.fuzzyfunctors.reductions.flipper.di.flipperModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class StoreApp : Application() {

    private val flipper by inject<Flipper>()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@StoreApp)
            loadKoinModules(
                listOf(
                    flipperModule,
                ),
            )
        }

        flipper.start()
    }
}

package com.fuzzyfunctors.reductions

import android.app.Application
import com.fuzzyfunctors.reductions.injection.Modules
import org.koin.android.ext.android.startKoin

class ReductionsApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, Modules.get(false))

    }

}

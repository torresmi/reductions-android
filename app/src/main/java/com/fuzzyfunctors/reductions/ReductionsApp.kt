package com.fuzzyfunctors.reductions

import android.app.Application
import com.fuzzyfunctors.reductions.debug.ui.DebugDrawer
import com.fuzzyfunctors.reductions.flipper.initializeFlipper
import com.fuzzyfunctors.reductions.injection.Modules
import logcat.AndroidLogcatLogger
import logcat.LogPriority
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ReductionsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidLogcatLogger.installOnDebuggableApp(this, minPriority = LogPriority.VERBOSE)
        DebugDrawer.initialize(this)

        initializeFlipper()

        startKoin {
            androidContext(this@ReductionsApp)
            modules(Modules.get(false))
        }
    }
}

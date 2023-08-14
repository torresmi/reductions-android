package com.fuzzyfunctors.reductions.flipper

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakEventListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.soloader.SoLoader
import com.fuzzyfunctors.reductions.flipper.api.Flipper
import leakcanary.LeakCanary

class FlipperImpl : Flipper {
    override fun initialize(application: Application) {
        SoLoader.init(application, false)

        if (!FlipperUtils.shouldEnableFlipper(application)) return

        setLeakCanaryListener()

        AndroidFlipperClient.getInstance(application)
            .apply {
                addPlugin(
                    InspectorFlipperPlugin(
                        application,
                        DescriptorMapping.withDefaults(),
                    ),
                )

                addPlugin(LeakCanary2FlipperPlugin())
                addPlugin(NavigationFlipperPlugin.getInstance())
            }.start()
    }

    private fun setLeakCanaryListener() {
        LeakCanary.config = LeakCanary.config.run {
            copy(eventListeners = eventListeners + FlipperLeakEventListener())
        }
    }
}

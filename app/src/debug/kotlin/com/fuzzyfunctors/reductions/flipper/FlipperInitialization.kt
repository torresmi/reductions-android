package com.fuzzyfunctors.reductions.flipper

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.soloader.SoLoader
import leakcanary.LeakCanary

fun Application.initializeFlipper() {
    SoLoader.init(this, false)

    setLeakCanaryListener()

    AndroidFlipperClient.getInstance(this)
        .apply {
            addPlugin(
                InspectorFlipperPlugin(
                    this@initializeFlipper,
                    DescriptorMapping.withDefaults(),
                )
            )

            addPlugin(LeakCanary2FlipperPlugin())
        }.start()
}

private fun setLeakCanaryListener() {
    LeakCanary.config = LeakCanary.config.copy(
        onHeapAnalyzedListener = FlipperLeakListener()
    )
}

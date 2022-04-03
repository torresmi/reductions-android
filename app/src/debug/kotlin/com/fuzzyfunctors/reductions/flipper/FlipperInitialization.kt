package com.fuzzyfunctors.reductions.flipper

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader

fun Application.initializeFlipper() {
    SoLoader.init(this, false)

    AndroidFlipperClient.getInstance(this)
        .apply {
            addPlugin(
                InspectorFlipperPlugin(
                    this@initializeFlipper,
                    DescriptorMapping.withDefaults(),
                )
            )
        }.start()
}

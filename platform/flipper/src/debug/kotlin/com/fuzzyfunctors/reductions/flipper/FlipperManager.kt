package com.fuzzyfunctors.reductions.flipper

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.core.FlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.leakcanary2.FlipperLeakEventListener
import com.facebook.flipper.plugins.leakcanary2.LeakCanary2FlipperPlugin
import com.facebook.flipper.plugins.navigation.NavigationFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.fuzzyfunctors.reductions.flipper.api.Flipper
import leakcanary.LeakCanary
import okhttp3.Interceptor

class FlipperManager(private val application: Application) : Flipper {
    private val networkFlipperPlugin: NetworkFlipperPlugin = NetworkFlipperPlugin()

    override fun start() {
        if (FlipperUtils.shouldEnableFlipper(application)) {
            SoLoader.init(application, false)
            AndroidFlipperClient.getInstance(application)
                .configure()
                .also { it.start() }
        }
    }

    fun buildNetworkInterceptor(): Interceptor {
        return FlipperOkhttpInterceptor(networkFlipperPlugin, true)
    }

    private fun FlipperClient.configure(): FlipperClient = apply {
        setLeakCanaryListener()

        addPlugin(
            InspectorFlipperPlugin(
                application,
                DescriptorMapping.withDefaults(),
            ),
        )

        addPlugin(networkFlipperPlugin)
        addPlugin(LeakCanary2FlipperPlugin())
        addPlugin(NavigationFlipperPlugin.getInstance())
    }

    private fun setLeakCanaryListener() {
        LeakCanary.config = LeakCanary.config.run {
            copy(eventListeners = eventListeners + FlipperLeakEventListener())
        }
    }
}

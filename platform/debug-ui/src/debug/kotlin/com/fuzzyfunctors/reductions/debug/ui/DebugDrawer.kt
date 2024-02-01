package com.fuzzyfunctors.reductions.debug.ui

import android.app.Application
import android.content.res.Resources
import com.fuzzyfunctors.reductions.debug.ui.api.DebugUI
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.common.configuration.Behavior
import com.pandulapeter.beagle.log.BeagleLogger
import com.pandulapeter.beagle.modules.AnimationDurationSwitchModule
import com.pandulapeter.beagle.modules.AppInfoButtonModule
import com.pandulapeter.beagle.modules.DeveloperOptionsButtonModule
import com.pandulapeter.beagle.modules.DeviceInfoModule
import com.pandulapeter.beagle.modules.HeaderModule
import com.pandulapeter.beagle.modules.KeylineOverlaySwitchModule
import com.pandulapeter.beagle.modules.LifecycleLogListModule
import com.pandulapeter.beagle.modules.LogListModule
import com.pandulapeter.beagle.modules.ScreenCaptureToolboxModule
import com.pandulapeter.beagle.modules.TextModule

object DebugDrawer : DebugUI {
    override fun initialize(application: Application) {
        Beagle.initialize(
            application,
            behavior = Behavior(
                logBehavior = Behavior.LogBehavior(
                    loggers = listOf(BeagleLogger),
                ),
            ),
        )

        DebugDrawerLogger.install()
        setupModules(application.resources)
    }

    override fun show() {
        Beagle.show()
    }

    override fun hide() {
        Beagle.hide()
    }

    private fun setupModules(resources: Resources) {
        Beagle.set(
            HeaderModule(resources.getString(R.string.debug_drawer_title)),
            // UI
            TextModule(
                resources.getString(R.string.debug_drawer_ui_section_title),
                TextModule.Type.SECTION_HEADER,
            ),
            KeylineOverlaySwitchModule(),
            AnimationDurationSwitchModule(),
            ScreenCaptureToolboxModule(),
            // Logging
            TextModule(
                resources.getString(R.string.debug_drawer_logs_section_title),
                TextModule.Type.SECTION_HEADER,
            ),
            LogListModule(),
            LifecycleLogListModule(),
            // Info
            TextModule(
                resources.getString(R.string.debug_drawer_device_section_title),
                TextModule.Type.SECTION_HEADER,
            ),
            DeviceInfoModule(),
            AppInfoButtonModule(),
            DeveloperOptionsButtonModule(),
        )
    }
}

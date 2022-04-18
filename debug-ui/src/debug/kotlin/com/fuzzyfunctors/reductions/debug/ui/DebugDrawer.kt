package com.fuzzyfunctors.reductions.debug.ui

import android.app.Application
import android.content.res.Resources
import com.pandulapeter.beagle.Beagle
import com.pandulapeter.beagle.modules.*

object DebugDrawer : DebugUI {

    override fun initialize(application: Application) {
        Beagle.initialize(application)
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

package com.fuzzyfunctors.reductions.debug.ui

import com.pandulapeter.beagle.Beagle
import logcat.LogPriority
import logcat.LogcatLogger

internal class DebugDrawerLogger : LogcatLogger {
    override fun log(priority: LogPriority, tag: String, message: String) {
        Beagle.log(message, label = tag)
    }

    companion object {
        fun install() {
            if (LogcatLogger.isInstalled) {
                LogcatLogger.install(DebugDrawerLogger())
            }
        }
    }
}

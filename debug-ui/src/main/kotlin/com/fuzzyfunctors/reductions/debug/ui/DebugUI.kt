package com.fuzzyfunctors.reductions.debug.ui

import android.app.Application

interface DebugUI {
    fun initialize(application: Application)

    fun show()

    fun hide()
}

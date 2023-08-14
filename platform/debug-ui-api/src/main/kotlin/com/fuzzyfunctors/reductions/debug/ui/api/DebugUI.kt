package com.fuzzyfunctors.reductions.debug.ui.api

import android.app.Application

interface DebugUI {
    fun initialize(application: Application)

    fun show()

    fun hide()
}

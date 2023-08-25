package com.fuzzyfunctors.reductions.flipper.di

import com.fuzzyfunctors.reductions.flipper.FlipperManager
import com.fuzzyfunctors.reductions.flipper.api.Flipper
import com.fuzzyfunctors.reductions.flipper.api.di.FLIPPER_NETWORK_INTERCEPTOR_NAME
import okhttp3.Interceptor
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val flipperModule = module {
    singleOf(::FlipperManager) bind Flipper::class
    factory<Interceptor>(named(FLIPPER_NETWORK_INTERCEPTOR_NAME)) {
        buildFlipperInterceptor(get())
    }
}

private fun buildFlipperInterceptor(flipperManager: FlipperManager): Interceptor =
    flipperManager.buildNetworkInterceptor()

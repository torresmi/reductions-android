package com.fuzzyfunctors.reductions.domain

sealed class LoadingFailure {
    data class Remote(val statusCode: Int) : LoadingFailure()

    data class Local(val error: Throwable) : LoadingFailure()
}

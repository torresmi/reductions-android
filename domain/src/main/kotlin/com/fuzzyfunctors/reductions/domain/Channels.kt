package com.fuzzyfunctors.reductions.domain

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel

@ExperimentalCoroutinesApi
fun <E> SendChannel<E>.safeOffer(value: E) = !isClosedForSend && try {
    offer(value)
} catch (e: CancellationException) {
    false
}

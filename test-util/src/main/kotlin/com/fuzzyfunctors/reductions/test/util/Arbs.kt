@file:Suppress("ktlint:filename")

package com.fuzzyfunctors.reductions.test.util

import io.kotest.property.Arb
import io.kotest.property.RandomSource
import io.kotest.property.arbitrary.next
import kotlin.random.Random

fun <A> Arb<A>.nextSeeded(seed: Long = 0): A = next(RandomSource(Random.Default, seed))

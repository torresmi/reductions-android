package com.fuzzyfunctors.reductions.testutil

import io.kotlintest.properties.Gen

class NullableGenerator<A : Any>(private val generator: Gen<A>) : Gen<A?> {

    override fun constants(): Iterable<A?> = listOf(null)

    override fun random(): Sequence<A?> = generateSequence {
        generator.firstRandom()
    }
}

package com.fuzzyfunctors.reductions.test.util

import com.appmattus.kotlinfixture.Fixture
import com.appmattus.kotlinfixture.config.ConfigurationBuilder
import com.appmattus.kotlinfixture.kotlinFixture
import io.kotest.property.Arb
import io.kotest.property.RandomSource
import io.kotest.property.Sample

inline fun <reified A> arb(
    vararg edgecases: A? = emptyArray(),
    crossinline builder: ConfigurationBuilder.() -> Unit = {},
): Arb<A> = object : Arb<A>() {
    private lateinit var randomSource: RandomSource

    private val fixture: Fixture by lazy {
        kotlinFixture {
            this.apply(builder)
            this.random = randomSource.random
        }
    }

    override fun edgecase(rs: RandomSource): A? =
        if (edgecases.isNotEmpty()) {
            edgecases.random(rs.random)
        } else {
            null
        }

    override fun sample(rs: RandomSource): Sample<A> {
        randomSource = rs
        return Sample(fixture())
    }
}

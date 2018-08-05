package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.game.CheapestPriceEver
import io.kotlintest.properties.Gen

class CheapestPriceEverGenerator : Gen<CheapestPriceEver> {
    override fun constants(): Iterable<CheapestPriceEver> = emptyList()

    override fun random(): Sequence<CheapestPriceEver> = generateSequence {
        CheapestPriceEver(
                price = randomString(),
                date = randomDate()
        )
    }
}

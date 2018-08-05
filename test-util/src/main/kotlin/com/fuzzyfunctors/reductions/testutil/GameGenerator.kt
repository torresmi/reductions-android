package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.game.Game
import io.kotlintest.properties.Gen

class GameGenerator : Gen<Game> {

    override fun constants(): Iterable<Game> = emptyList()

    override fun random(): Sequence<Game> = generateSequence {
        Game(
                id = randomString(),
                title = randomString(),
                cheapestPriceEver = CheapestPriceEverGenerator().firstRandom(),
                deals = Gen.map(Gen.string(), DealInfoGenerator()).firstRandom()
        )
    }

    class DealInfoGenerator : Gen<Game.DealInfo> {

        override fun constants(): Iterable<Game.DealInfo> = emptyList()

        override fun random(): Sequence<Game.DealInfo> = generateSequence {
            Game.DealInfo(
                    dealId = randomString(),
                    storeId = randomString(),
                    price = randomString(),
                    retailPrice = randomString(),
                    savings = randomString()
            )
        }
    }
}

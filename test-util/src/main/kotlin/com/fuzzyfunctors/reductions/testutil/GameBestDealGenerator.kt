package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import io.kotlintest.properties.Gen

class GameBestDealGenerator : Gen<GameBestDeal> {

    override fun constants(): Iterable<GameBestDeal> = emptyList()

    override fun random(): Sequence<GameBestDeal> = generateSequence {
        GameBestDeal(
                gameID = randomString(),
                steamAppID = NullableGenerator(Gen.string()).firstRandom(),
                cheapest = randomString(),
                cheapestDealID = randomString(),
                title = randomString(),
                thumb = randomString()
        )
    }
}

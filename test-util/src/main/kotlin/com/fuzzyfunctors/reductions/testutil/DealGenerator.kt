package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.game.CheapestPriceEver
import io.kotlintest.properties.Gen

class DealGenerator : Gen<Deal> {
    override fun constants(): Iterable<Deal> = listOf(
            Deal(
                    id = randomString(),
                    storeId = randomString(),
                    gameId = randomString(),
                    title = randomString(),
                    saleData = null,
                    normalPrice = randomString(),
                    metacriticData = null,
                    steamData = null,
                    releaseDate = randomDate(),
                    lastChange = null,
                    iconLink = randomString(),
                    gameInfo = null,
                    cheaperStores = null
            )
    )

    override fun random(): Sequence<Deal> = generateSequence {
        Deal(
                    id = randomString(),
                    storeId = randomString(),
                    gameId = randomString(),
                    title = randomString(),
                    saleData = Deal.SaleData(
                            price = randomString(),
                            savings = randomString(),
                            rating = randomString()
                    ),
                    normalPrice = randomString(),
                    metacriticData = Deal.MetacriticData(
                            link = randomString(),
                            score = randomString()
                    ),
                    steamData = Deal.SteamData(
                            appId = randomString(),
                            ratingText = NullableGenerator(Gen.string()).firstRandom(),
                            ratingCount = randomString(),
                            ratingPercent = randomString()
                    ),
                    releaseDate = randomDate(),
                    lastChange = randomDate(),
                    iconLink = randomString(),
                    gameInfo = Deal.GameInfo(
                            publisher = randomString(),
                            steamWorks = randomBool(),
                            cheapestPriceEver = CheapestPriceEver(
                                    price = randomString(),
                                    date = randomDate()
                            )
                    ),
                    cheaperStores = Gen.map(Gen.string(), CheaperStoreGenerator()).firstRandom()
            )
    }

    class CheaperStoreGenerator : Gen<Deal.CheaperStore> {
        override fun constants(): Iterable<Deal.CheaperStore> = emptyList()

        override fun random(): Sequence<Deal.CheaperStore> = generateSequence {
            Deal.CheaperStore(
                    storeId = randomString(),
                    dealId = randomString(),
                    salePrice = randomString(),
                    normalPrice = randomString()
            )
        }
    }
}

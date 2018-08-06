package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.core.game.CheapestPriceEver
import io.kotlintest.properties.Gen

class DealInfoGenerator : Gen<DealInfo> {
    override fun constants(): Iterable<DealInfo> = listOf(
            random().first().copy(
                    salePrice = null,
                    metacriticData = null,
                    steamData = null
            )
    )

    override fun random(): Sequence<DealInfo> = generateSequence {
        DealInfo(
                id = randomString(),
                storeId = randomString(),
                gameId = randomString(),
                title = randomString(),
                salePrice = randomNullableString(),
                normalPrice = randomString(),
                metacriticData = NullableGenerator(DealGenerator.MetacriticDataGenerator()).firstRandom(),
                steamData = NullableGenerator(DealGenerator.SteamDataGenerator()).firstRandom(),
                releaseDate = randomDate(),
                publisher = randomString(),
                steamWorks = randomBool(),
                iconLink = randomString(),
                cheapestPriceEver = CheapestPriceEver(
                        price = randomString(),
                        date = randomDate()
                ),
                cheaperStores = Gen.map(Gen.string(), CheaperStoreGenerator()).firstRandom()
        )
    }

    class CheaperStoreGenerator : Gen<DealInfo.CheaperStore> {
        override fun constants(): Iterable<DealInfo.CheaperStore> = emptyList()

        override fun random(): Sequence<DealInfo.CheaperStore> = generateSequence {
            DealInfo.CheaperStore(
                    storeId = randomString(),
                    dealId = randomString(),
                    salePrice = randomString(),
                    normalPrice = randomString()
            )
        }
    }
}

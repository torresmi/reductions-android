package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.deal.Deal
import io.kotlintest.properties.Gen

class DealGenerator : Gen<Deal> {
    override fun constants(): Iterable<Deal> = listOf(
            random().first().copy(
                    saleData = null,
                    metacriticData = null,
                    steamData = null,
                    lastChange = null
            )
    )

    override fun random(): Sequence<Deal> = generateSequence {
        Deal(
                id = randomString(),
                storeId = randomString(),
                gameId = randomString(),
                title = randomString(),
                saleData = NullableGenerator(SaleDataGenerator()).firstRandom(),
                normalPrice = randomString(),
                metacriticData = NullableGenerator(MetacriticDataGenerator()).firstRandom(),
                steamData = NullableGenerator(SteamDataGenerator()).firstRandom(),
                releaseDate = randomDate(),
                lastChange = randomDate(),
                iconLink = randomString()
        )
    }

    class SaleDataGenerator : Gen<Deal.SaleData> {
        override fun constants(): Iterable<Deal.SaleData> = emptyList()

        override fun random(): Sequence<Deal.SaleData> = generateSequence {
            Deal.SaleData(
                    price = randomString(),
                    savings = randomString(),
                    rating = Gen.choose(0, 100).firstRandom().toString()
            )
        }
    }

    class SteamDataGenerator : Gen<Deal.SteamData> {
        override fun constants(): Iterable<Deal.SteamData> = emptyList()

        override fun random(): Sequence<Deal.SteamData> = generateSequence {
            Deal.SteamData(
                    appId = randomString(),
                    ratingText = randomString(),
                    ratingCount = randomString(),
                    ratingPercent = randomPercent()
            )
        }
    }

    class MetacriticDataGenerator : Gen<Deal.MetacriticData> {
        override fun constants(): Iterable<Deal.MetacriticData> = emptyList()

        override fun random(): Sequence<Deal.MetacriticData> = generateSequence {
            Deal.MetacriticData(
                    link = randomString(),
                    score = Gen.choose(0, 100).firstRandom().toString()
            )
        }
    }
}

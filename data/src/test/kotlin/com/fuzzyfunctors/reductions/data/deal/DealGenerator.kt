package com.fuzzyfunctors.reductions.data.deal

import com.fuzzyfunctors.reductions.testutil.NullableGenerator
import com.fuzzyfunctors.reductions.testutil.firstRandom
import com.fuzzyfunctors.reductions.testutil.randomInt
import com.fuzzyfunctors.reductions.testutil.randomLong
import com.fuzzyfunctors.reductions.testutil.randomNullableInt
import com.fuzzyfunctors.reductions.testutil.randomNullableLong
import com.fuzzyfunctors.reductions.testutil.randomNullableString
import com.fuzzyfunctors.reductions.testutil.randomPercent
import com.fuzzyfunctors.reductions.testutil.randomString
import io.kotlintest.properties.Gen

class DealGenerator : Gen<Deal> {

    private val minPrice = 500
    private val maxPrice = 6000

    override fun constants(): Iterable<Deal> =
            listOf(
                    onSale(),
                    notOnSale(),
                    onSteam(),
                    notOnSteam(),
                    onMetacritic(),
                    notOnMetacritic()
            )

    override fun random(): Sequence<Deal> = generateSequence {
        Deal(
                internalName = randomString(),
                title = randomString(),
                metacriticLink = randomNullableString(),
                dealID = randomString(),
                storeID = randomString(),
                gameID = randomString(),
                salePrice = randomString(),
                normalPrice = randomString(),
                isOnSale = Gen.choose(0, 1).firstRandom().toString(),
                savings = randomString(),
                metacriticScore = randomNullableInt().toString(),
                steamRatingText = randomNullableString(),
                steamRatingCount = randomNullableInt().toString(),
                steamRatingPercent = NullableGenerator(Gen.choose(0, 100)).firstRandom().toString(),
                steamAppID = randomNullableString(),
                releaseDate = randomLong(),
                lastChange = randomNullableLong(),
                dealRating = randomString(),
                thumb = randomString()
        )
    }

    fun notOnSale(): Deal {
        val price = Gen.choose(minPrice, maxPrice).firstRandom().toString()
        return random().first().copy(
                normalPrice = price,
                salePrice = price,
                savings = "0",
                isOnSale = "0"
        )
    }

    fun onSale(): Deal {
        val price = Gen.choose(minPrice, maxPrice).firstRandom()
        val sale = Gen.choose(minPrice, maxPrice - 1).firstRandom()
        val savings = price - sale
        return random().first().copy(
                normalPrice = price.toString(),
                salePrice = sale.toString(),
                isOnSale = "1",
                savings = savings.toString()
        )
    }

    fun onSteam(): Deal =
            random().first().copy(
                    steamAppID = randomString(),
                    steamRatingPercent = randomPercent(),
                    steamRatingCount = randomInt().toString(),
                    steamRatingText = randomString()
            )

    fun notOnSteam(): Deal =
            random().first().copy(
                    steamAppID = null,
                    steamRatingPercent = null,
                    steamRatingCount = null,
                    steamRatingText = null
            )

    fun onMetacritic(): Deal =
            random().first().copy(
                    metacriticScore = randomInt().toString(),
                    metacriticLink = randomString()
            )

    fun notOnMetacritic(): Deal =
            random().first().copy(
                    metacriticScore = null,
                    metacriticLink = null
            )
}

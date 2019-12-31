package com.fuzzyfunctors.reductions.data.deal

import com.fuzzyfunctors.reductions.testutil.NullableGenerator
import com.fuzzyfunctors.reductions.testutil.firstRandom
import com.fuzzyfunctors.reductions.testutil.randomInt
import com.fuzzyfunctors.reductions.testutil.randomLong
import com.fuzzyfunctors.reductions.testutil.randomNullableString
import com.fuzzyfunctors.reductions.testutil.randomPercent
import com.fuzzyfunctors.reductions.testutil.randomString
import io.kotlintest.properties.Gen

class DealInfoResponseGenerator : Gen<DealInfoResponse> {

    private val minPrice = 500
    private val maxPrice = 6000

    override fun constants(): Iterable<DealInfoResponse> =
        listOf(
            onSale(),
            notOnSale(),
            onSteam(),
            notOnSteam(),
            onMetacritic(),
            notOnMetacritic()
        )

    override fun random(): Sequence<DealInfoResponse> = generateSequence {
        DealInfoResponse(
            gameInfo = DealInfoResponse.GameInfo(
                storeID = randomString(),
                gameID = randomString(),
                name = randomString(),
                steamAppID = randomNullableString(),
                salePrice = randomString(),
                retailPrice = randomString(),
                steamRatingText = randomNullableString(),
                steamRatingPercent = randomPercent(),
                steamRatingCount = NullableGenerator(Gen.choose(0, 100)).firstRandom().toString(),
                steamworks = Gen.choose(0, 1).firstRandom().toString(),
                metacriticScore = randomNullableString(),
                metacriticLink = randomNullableString(),
                releaseDate = randomLong(),
                publisher = randomString(),
                thumb = randomString()
            ),
            cheaperStores = Gen.list(CheaperStoreGenerator()).firstRandom(),
            cheapestPrice = DealInfoResponse.CheapestPrice(
                price = randomString(),
                date = randomLong()
            )
        )
    }

    fun notOnSale(): DealInfoResponse {
        val price = Gen.choose(minPrice, maxPrice).firstRandom().toString()
        val response = random().first()
        return response.copy(
            gameInfo = response.gameInfo.copy(
                salePrice = price,
                retailPrice = price
            )
        )
    }

    fun onSale(): DealInfoResponse {
        val price = Gen.choose(minPrice, maxPrice).firstRandom()
        val sale = Gen.choose(minPrice, maxPrice - 1).firstRandom()
        val response = random().first()
        return response.copy(
            gameInfo = response.gameInfo.copy(
                salePrice = sale.toString(),
                retailPrice = price.toString()
            )
        )
    }

    fun onSteam(): DealInfoResponse {
        val response = random().first()
        return response.copy(
            gameInfo = response.gameInfo.copy(
                steamAppID = randomString(),
                steamRatingPercent = randomPercent(),
                steamRatingCount = randomInt().toString(),
                steamRatingText = randomString(),
                steamworks = "1"
            )
        )
    }

    fun notOnSteam(): DealInfoResponse {
        val response = random().first()
        return response.copy(
            gameInfo = response.gameInfo.copy(
                steamAppID = null,
                steamRatingPercent = null,
                steamRatingCount = null,
                steamRatingText = null,
                steamworks = "0"
            )
        )
    }

    fun onMetacritic(): DealInfoResponse {
        val response = random().first()
        return response.copy(
            gameInfo = response.gameInfo.copy(
                metacriticScore = randomInt().toString(),
                metacriticLink = randomString()
            )
        )
    }

    fun notOnMetacritic(): DealInfoResponse {
        val response = random().first()
        return response.copy(
            gameInfo = response.gameInfo.copy(
                metacriticScore = null,
                metacriticLink = null
            )
        )
    }

    class CheaperStoreGenerator : Gen<DealInfoResponse.CheaperStore> {
        override fun constants(): Iterable<DealInfoResponse.CheaperStore> = emptyList()

        override fun random(): Sequence<DealInfoResponse.CheaperStore> = generateSequence {
            DealInfoResponse.CheaperStore(
                dealID = randomString(),
                storeID = randomString(),
                salePrice = randomString(),
                retailPrice = randomString()
            )
        }
    }
}

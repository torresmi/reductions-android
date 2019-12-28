package com.fuzzyfunctors.reductions.data.deal

import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.network.toCore
import com.fuzzyfunctors.reductions.testutil.randomString
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec
import java.util.Date

class DealInfoResponseMapperTest : DescribeSpec() {

    val generator = DealInfoResponseGenerator()

    val dealId = randomString()

    init {

        describe("map to core deal info") {

            context("has sale data") {
                val onSale = generator.onSale()

                it("should parse with sale price") {
                    val result = onSale.toCore(dealId)

                    result.salePrice shouldBe onSale.gameInfo.salePrice
                    assertCommonProperties(onSale, result)
                }
            }

            context("does not have sale data") {
                val notOnSale = generator.notOnSale()

                it("should parse without sale data") {
                    val result = notOnSale.toCore(dealId)

                    result.salePrice shouldBe null
                    assertCommonProperties(notOnSale, result)
                }
            }

            context("has steam data") {
                val onSteam = generator.onSteam()

                it("should parse with steam data") {
                    val result = onSteam.toCore(dealId)

                    val expected = Deal.SteamData(
                            appId = onSteam.gameInfo.steamAppID!!,
                            ratingText = onSteam.gameInfo.steamRatingText!!,
                            ratingCount = onSteam.gameInfo.steamRatingCount!!,
                            ratingPercent = onSteam.gameInfo.steamRatingPercent!!
                    )

                    result.steamData shouldBe expected
                    assertCommonProperties(onSteam, result)
                }
            }

            context("does not have steam data") {
                val notOnSteam = generator.notOnSteam()

                it("should parse without steam data") {
                    val result = notOnSteam.toCore(dealId)

                    result.steamData shouldBe null
                    assertCommonProperties(notOnSteam, result)
                }
            }

            context("has metacritic data") {
                val onMetacritic = generator.onMetacritic()

                it("should parse with metacritic data") {
                    val result = onMetacritic.toCore(dealId)

                    result.metacriticData shouldNotBe null
                    assertCommonProperties(onMetacritic, result)
                }
            }

            context("does not have metacritic data") {
                val notOnMetacritic = generator.notOnMetacritic()

                it("should parse without metacritic data") {
                    val result = notOnMetacritic.toCore(dealId)

                    result.metacriticData shouldBe null
                    assertCommonProperties(notOnMetacritic, result)
                }
            }
        }
    }

    fun assertCommonProperties(response: DealInfoResponse, result: DealInfo) {
        result.id shouldBe dealId
        result.storeId shouldBe response.gameInfo.storeID
        result.gameId shouldBe response.gameInfo.gameID
        result.title shouldBe response.gameInfo.name
        result.normalPrice shouldBe response.gameInfo.retailPrice
        result.releaseDate shouldBe Date(response.gameInfo.releaseDate)
        result.iconLink shouldBe response.gameInfo.thumb
        result.publisher shouldBe response.gameInfo.publisher
        result.steamWorks shouldBe (response.gameInfo.steamworks == "1")
    }
}

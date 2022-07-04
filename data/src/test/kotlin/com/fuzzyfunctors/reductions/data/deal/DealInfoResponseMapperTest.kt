package com.fuzzyfunctors.reductions.data.deal

import com.appmattus.kotlinfixture.kotlinFixture
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.network.toCore
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.Date

class DealInfoResponseMapperTest : DescribeSpec() {

    val dealId = kotlinFixture().invoke<String>()

    init {

        describe("map to core deal info") {

            context("has sale data") {
                val onSale = DealInfoResponseGeneration.onSale

                it("should parse with sale price") {
                    val result = onSale.toCore(dealId)

                    result.salePrice shouldBe onSale.gameInfo.salePrice
                    assertCommonProperties(onSale, result)
                }
            }

            context("does not have sale data") {
                val notOnSale = DealInfoResponseGeneration.notOnSale

                it("should parse without sale data") {
                    val result = notOnSale.toCore(dealId)

                    result.salePrice shouldBe null
                    assertCommonProperties(notOnSale, result)
                }
            }

            context("has steam data") {
                val onSteam = DealInfoResponseGeneration.onSteam

                it("should parse with steam data") {
                    val result = onSteam.toCore(dealId)

                    val expected = Deal.SteamData(
                        appId = onSteam.gameInfo.steamAppID!!,
                        ratingText = onSteam.gameInfo.steamRatingText!!,
                        ratingCount = onSteam.gameInfo.steamRatingCount!!,
                        ratingPercent = onSteam.gameInfo.steamRatingPercent!!,
                    )

                    result.steamData shouldBe expected
                    assertCommonProperties(onSteam, result)
                }
            }

            context("does not have steam data") {
                val notOnSteam = DealInfoResponseGeneration.notOnSteam

                it("should parse without steam data") {
                    val result = notOnSteam.toCore(dealId)

                    result.steamData shouldBe null
                    assertCommonProperties(notOnSteam, result)
                }
            }

            context("has metacritic data") {
                val onMetacritic = DealInfoResponseGeneration.onMetacritic

                it("should parse with metacritic data") {
                    val result = onMetacritic.toCore(dealId)

                    result.metacriticData shouldNotBe null
                    assertCommonProperties(onMetacritic, result)
                }
            }

            context("does not have metacritic data") {
                val notOnMetacritic = DealInfoResponseGeneration.notOnMetacritic

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

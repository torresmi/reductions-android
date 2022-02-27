package com.fuzzyfunctors.reductions.data.deal

import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.data.network.toCore
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import java.util.Date
import com.fuzzyfunctors.reductions.data.deal.Deal as ApiDeal

class DealMapperTest : DescribeSpec() {

    val generator = DealResponseGeneration.arb()

    init {

        describe("map to core deal") {

            context("has sale data") {
                val onSale = DealResponseGeneration.onSale

                it("should parse with sale data") {
                    val result = onSale.toCore()

                    val expected = Deal.SaleData(
                        price = onSale.salePrice,
                        savings = onSale.savings,
                        rating = onSale.dealRating
                    )

                    result.saleData shouldBe expected
                    assertCommonProperties(onSale, result)
                }
            }

            context("does not have sale data") {
                val notOnSale = DealResponseGeneration.notOnSale

                it("should parse without sale data") {
                    val result = notOnSale.toCore()

                    result.saleData shouldBe null
                    assertCommonProperties(notOnSale, result)
                }
            }

            context("has steam data") {
                val onSteam = DealResponseGeneration.onSteam

                it("should parse with steam data") {
                    val result = onSteam.toCore()

                    val expected = Deal.SteamData(
                        appId = onSteam.steamAppID!!,
                        ratingText = onSteam.steamRatingText!!,
                        ratingCount = onSteam.steamRatingCount!!,
                        ratingPercent = onSteam.steamRatingPercent!!
                    )

                    result.steamData shouldBe expected
                    assertCommonProperties(onSteam, result)
                }
            }

            context("does not have steam data") {
                val notOnSteam = DealResponseGeneration.notOnSteam

                it("should parse without steam data") {
                    val result = notOnSteam.toCore()

                    result.steamData shouldBe null
                    assertCommonProperties(notOnSteam, result)
                }
            }

            context("has metacritic data") {
                val onMetacritic = DealResponseGeneration.onMetacritic

                it("should parse with metacritic data") {
                    val result = onMetacritic.toCore()

                    result.metacriticData shouldNotBe null
                    assertCommonProperties(onMetacritic, result)
                }
            }

            context("does not have metacritic data") {
                val notOnMetacritic = DealResponseGeneration.notOnMetacritic

                it("should parse without metacritic data") {
                    val result = notOnMetacritic.toCore()

                    result.metacriticData shouldBe null
                    assertCommonProperties(notOnMetacritic, result)
                }
            }
        }
    }

    fun assertCommonProperties(deal: ApiDeal, result: Deal) {
        result.id shouldBe deal.dealID
        result.storeId shouldBe deal.storeID
        result.gameId shouldBe deal.gameID
        result.title shouldBe deal.title
        result.normalPrice shouldBe deal.normalPrice
        result.releaseDate shouldBe Date(deal.releaseDate)
        result.lastChange shouldBe deal.lastChange?.let { Date(it) }
        result.iconLink shouldBe deal.thumb
    }
}

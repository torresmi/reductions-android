package com.fuzzyfunctors.reductions.data.deal

import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.data.deal.Deal as ApiDeal
import com.fuzzyfunctors.reductions.data.network.toCore
import io.kotlintest.shouldBe
import io.kotlintest.shouldNotBe
import io.kotlintest.specs.DescribeSpec
import java.util.Date

class DealMapperTest : DescribeSpec() {

    val generator = DealGenerator()

    init {

        describe("map to core deal") {

            context("has sale data") {
                val onSale = generator.onSale()

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
                val notOnSale = generator.notOnSale()

                it("should parse without sale data") {
                    val result = notOnSale.toCore()

                    result.saleData shouldBe null
                    assertCommonProperties(notOnSale, result)
                }
            }

            context("has steam data") {
                val onSteam = generator.onSteam()

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
                val notOnSteam = generator.notOnSteam()

                it("should parse without steam data") {
                    val result = notOnSteam.toCore()

                    result.steamData shouldBe null
                    assertCommonProperties(notOnSteam, result)
                }
            }

            context("has metacritic data") {
                val onMetacritic = generator.onMetacritic()

                it("should parse with metacritic data") {
                    val result = onMetacritic.toCore()

                    result.metacriticData shouldNotBe null
                    assertCommonProperties(onMetacritic, result)
                }
            }

            context("does not have metacritic data") {
                val notOnMetacritic = generator.notOnMetacritic()

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
        result.gameInfo shouldBe null
        result.cheaperStores shouldBe null
    }
}

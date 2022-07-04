package com.fuzzyfunctors.reductions.data.deal

import com.appmattus.kotlinfixture.kotlinFixture
import com.fuzzyfunctors.reductions.test.util.arb
import com.fuzzyfunctors.reductions.test.util.fake
import io.kotest.property.Arb

object DealResponseGeneration {
    val baseFixture = kotlinFixture {
        property(Deal::salePrice) {
            fake { money.amount(0..60) }
        }
        property(Deal::normalPrice) {
            fake { money.amount(0..60) }
        }
    }

    private fun saleFixture(
        salePrice: String,
        retailPrice: String,
        isOnSale: Boolean,
    ) = baseFixture.new {
        property(Deal::salePrice) { salePrice }
        property(Deal::normalPrice) { retailPrice }
        property(Deal::isOnSale) { if (isOnSale) "1" else "0" }
    }

    val onSale = saleFixture(salePrice = "20.00", retailPrice = "60.00", isOnSale = true)
        .invoke<Deal>()

    val notOnSale = saleFixture(salePrice = "60.00", retailPrice = "60.00", isOnSale = false)
        .invoke<Deal>()

    private fun steamFixture(
        appId: String?,
        ratingPercent: String?,
        ratingCount: String?,
        ratingText: String?,
    ) = DealInfoResponseGeneration.baseFixture.new {
        property(Deal::steamAppID) { appId }
        property(Deal::steamRatingPercent) { ratingPercent }
        property(Deal::steamRatingCount) { ratingCount }
        property(Deal::steamRatingText) { ratingText }
    }

    val onSteam = steamFixture(
        appId = "1",
        ratingPercent = "100",
        ratingCount = "1",
        ratingText = "good",
    ).invoke<Deal>()

    val notOnSteam = steamFixture(
        appId = null,
        ratingPercent = null,
        ratingCount = null,
        ratingText = null,
    ).invoke<Deal>()

    val onMetacritic = baseFixture.new {
        property(Deal::metacriticLink) { "wwww.metacritic.com" }
        property(Deal::metacriticScore) { "1" }
    }.invoke<Deal>()

    val notOnMetacritic = baseFixture.new {
        property(Deal::metacriticLink) { null }
        property(Deal::metacriticScore) { null }
    }.invoke<Deal>()

    val edgeCases = listOf(
        onSale,
        notOnSale,
        onSteam,
        notOnSteam,
        onMetacritic,
        notOnMetacritic,
    )

    fun arb(): Arb<Deal> = arb(*edgeCases.toTypedArray())
}

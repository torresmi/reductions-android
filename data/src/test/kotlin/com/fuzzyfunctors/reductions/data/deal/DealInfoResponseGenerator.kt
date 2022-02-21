package com.fuzzyfunctors.reductions.data.deal

import com.appmattus.kotlinfixture.kotlinFixture
import com.fuzzyfunctors.reductions.test.util.fake
import com.fuzzyfunctors.reductions.test.util.arb
import io.kotest.property.Arb

object DealInfoResponseGeneration {
    val baseFixture = kotlinFixture {
        property(DealInfoResponse.GameInfo::salePrice) {
            fake { money.amount(1..60) }
        }
        property(DealInfoResponse.GameInfo::retailPrice) {
            fake { money.amount(1..60) }
        }
    }

    private fun saleFixture(
        salePrice: String,
        retailPrice: String
    ) = baseFixture.new {
        property(DealInfoResponse.GameInfo::salePrice) { salePrice }
        property(DealInfoResponse.GameInfo::retailPrice) { retailPrice }
    }

    val onSale = saleFixture(salePrice = "20.00", retailPrice = "60.00")
        .invoke<DealInfoResponse>()

    val notOnSale = saleFixture(salePrice = "60.00", retailPrice = "60.00")
        .invoke<DealInfoResponse>()

    private fun steamFixture(
        appId: String?,
        ratingPercent: String?,
        ratingCount: String?,
        ratingText: String?,
        onSteam: Boolean
    ) = baseFixture.new {
        property(DealInfoResponse.GameInfo::steamAppID) { appId }
        property(DealInfoResponse.GameInfo::steamRatingPercent) { ratingPercent }
        property(DealInfoResponse.GameInfo::steamRatingCount) { ratingCount }
        property(DealInfoResponse.GameInfo::steamRatingText) { ratingText }
        property(DealInfoResponse.GameInfo::steamworks) { if (onSteam) "1" else "0" }
    }

    val onSteam = steamFixture(
        appId = "1",
        ratingPercent = "100",
        ratingCount = "1",
        ratingText = "good",
        onSteam = true
    ).invoke<DealInfoResponse>()

    val notOnSteam = steamFixture(
        appId = null,
        ratingPercent = null,
        ratingCount = null,
        ratingText = null,
        onSteam = false
    ).invoke<DealInfoResponse>()

    private fun metacriticFixture(
        score: String?,
        link: String?
    ) = baseFixture.new {
        property(DealInfoResponse.GameInfo::metacriticScore) { score }
        property(DealInfoResponse.GameInfo::metacriticLink) { link }
    }

    val onMetacritic = metacriticFixture(score = "100", link = "www.metacritic.com")
        .invoke<DealInfoResponse>()
    val notOnMetacritic = metacriticFixture(score = null, link = null)
        .invoke<DealInfoResponse>()

    val edgeCases = listOf(
        onSale,
        notOnSale,
        onSteam,
        notOnSteam,
        onMetacritic,
        notOnMetacritic
    )

    fun arb(): Arb<DealInfoResponse> = arb(*edgeCases.toTypedArray())
}

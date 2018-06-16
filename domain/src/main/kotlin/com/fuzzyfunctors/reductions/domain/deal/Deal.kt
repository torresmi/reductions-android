package com.fuzzyfunctors.reductions.domain.deal

import java.util.Date

data class Deal(
        val id: String,
        val title: String,
        val storeId: String,
        val gameId: String,
        val saleData: SaleData?,
        val normalPrice: String,
        val metacriticData: MetacriticData?,
        val steamData: SteamData?,
        val releaseDate: Date,
        val lastChange: Date?,
        val iconLink: String
) {

    data class SaleData(
            val price: String,
            val savings: String,
            val rating: String
    )

    data class MetacriticData(
            val link: String,
            val score: String
    )

    data class SteamData(
            val appId: String,
            val ratingText: String?,
            val ratingCount: String,
            val ratingPercent: String
    )
}

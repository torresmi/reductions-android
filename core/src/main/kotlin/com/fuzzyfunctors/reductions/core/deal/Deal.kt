package com.fuzzyfunctors.reductions.core.deal

import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.core.store.StoreId
import java.util.Date

typealias DealId = String

data class Deal(
        val id: DealId,
        val storeId: StoreId,
        val gameId: GameId,
        val title: String,
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

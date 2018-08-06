package com.fuzzyfunctors.reductions.core.deal

import com.fuzzyfunctors.reductions.core.game.CheapestPriceEver
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.core.store.StoreId
import java.util.Date

data class DealInfo(
        val id: DealId,
        val storeId: StoreId,
        val gameId: GameId,
        val title: String,
        val salePrice: String?,
        val normalPrice: String,
        val metacriticData: Deal.MetacriticData?,
        val steamData: Deal.SteamData?,
        val publisher: String,
        val steamWorks: Boolean,
        val releaseDate: Date,
        val iconLink: String,
        val cheaperStores: Map<StoreId, CheaperStore>,
        val cheapestPriceEver: CheapestPriceEver
) {

    data class CheaperStore(
            val storeId: StoreId,
            val dealId: DealId,
            val salePrice: String,
            val normalPrice: String
    )
}

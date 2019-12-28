package com.fuzzyfunctors.reductions.core.game

import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.store.StoreId

typealias GameId = String

data class Game(
    val id: GameId,
    val steamAppId: String?,
    val title: String,
    val cheapestPriceEver: CheapestPriceEver,
    val deals: Map<DealId, DealInfo>
) {

    data class DealInfo(
        val dealId: DealId,
        val storeId: StoreId,
        val price: String,
        val retailPrice: String,
        val savings: String
    )
}

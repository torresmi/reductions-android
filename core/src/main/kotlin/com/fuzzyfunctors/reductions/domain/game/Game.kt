package com.fuzzyfunctors.reductions.domain.game

import com.fuzzyfunctors.reductions.domain.deal.DealId
import com.fuzzyfunctors.reductions.domain.store.StoreId

typealias GameId = String

data class Game(
        val id: GameId,
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


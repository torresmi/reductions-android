package com.fuzzyfunctors.reductions.data.game

class GameInfoResponse(
        val info: Info,
        val cheapestPriceEver: CheapestPriceEver,
        val deals: List<Deal>
) {

    data class Info(
            val title: String,
            val steamAppID: String?
    )

    data class CheapestPriceEver(
            val price: String,
            val date: Long
    )

    data class Deal(
            val storeID: String,
            val dealID: String,
            val price: String,
            val retailPrice: String,
            val savings: String
    )

}

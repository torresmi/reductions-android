package com.fuzzyfunctors.reductions.data.deal

data class DealInfoResponse(
    val gameInfo: GameInfo,
    val cheaperStores: List<CheaperStore>,
    val cheapestPrice: CheapestPrice,
) {

    data class GameInfo(
        val storeID: String,
        val gameID: String,
        val name: String,
        val steamAppID: String?,
        val salePrice: String,
        val retailPrice: String,
        val steamRatingText: String?,
        val steamRatingPercent: String?,
        val steamRatingCount: String?,
        val metacriticScore: String?,
        val metacriticLink: String?,
        val releaseDate: Long,
        val publisher: String,
        val steamworks: String,
        val thumb: String,
    )

    data class CheaperStore(
        val dealID: String,
        val storeID: String,
        val salePrice: String,
        val retailPrice: String,
    )

    data class CheapestPrice(
        val price: String,
        val date: Long,
    )
}

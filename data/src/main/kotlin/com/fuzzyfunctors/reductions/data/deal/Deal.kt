package com.fuzzyfunctors.reductions.data.deal

data class Deal(
    val internalName: String,
    val title: String,
    val metacriticLink: String?,
    val dealID: String,
    val storeID: String,
    val gameID: String,
    val salePrice: String,
    val normalPrice: String,
    val isOnSale: String,
    val savings: String,
    val metacriticScore: String?,
    val steamRatingText: String?,
    val steamRatingCount: String?,
    val steamRatingPercent: String?,
    val steamAppID: String?,
    val releaseDate: Long,
    val lastChange: Long?,
    val dealRating: String,
    val thumb: String,
)

package com.fuzzyfunctors.reductions.core.game

data class GameBestDeal(
        val gameId: String,
        val steamAppId: String?,
        val cheapest: String,
        val cheapestDealId: String,
        val title: String,
        val thumb: String
)

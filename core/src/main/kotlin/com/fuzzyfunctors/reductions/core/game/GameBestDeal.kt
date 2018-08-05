package com.fuzzyfunctors.reductions.core.game

data class GameBestDeal(
        val gameID: String,
        val steamAppID: String?,
        val cheapest: String,
        val cheapestDealID: String,
        val title: String,
        val thumb: String
)

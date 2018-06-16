package com.fuzzyfunctors.reductions.domain.game

data class GameBestDeal(
        val gameID: String,
        val steamAppID: String?,
        val cheapest: String,
        val cheapestDealID: String,
        val external: String,
        val thumb: String
)

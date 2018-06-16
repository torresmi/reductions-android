package com.fuzzyfunctors.reductions.data.alert

data class Alert(
        val action: String,
        val email: String,
        val gameID: Int,
        val price: Double
)

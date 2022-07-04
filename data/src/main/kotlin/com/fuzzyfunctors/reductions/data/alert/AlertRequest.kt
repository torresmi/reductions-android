package com.fuzzyfunctors.reductions.data.alert

data class AlertRequest(
    val action: String,
    val email: String,
    val gameID: Int,
    val price: Double?,
)

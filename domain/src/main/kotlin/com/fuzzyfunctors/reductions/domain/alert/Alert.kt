package com.fuzzyfunctors.reductions.domain.alert

data class Alert(
        val email: String,
        val gameID: String,
        val price: String?
)

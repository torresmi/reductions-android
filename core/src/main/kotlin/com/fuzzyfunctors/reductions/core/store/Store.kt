package com.fuzzyfunctors.reductions.core.store

typealias StoreId = String

data class Store(
    val id: StoreId,
    val name: String,
    val isActive: Boolean,
    val images: Images
) {

    data class Images(
        val banner: String,
        val logo: String,
        val icon: String
    )
}

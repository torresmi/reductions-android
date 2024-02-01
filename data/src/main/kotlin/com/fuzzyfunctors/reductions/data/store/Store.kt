package com.fuzzyfunctors.reductions.data.store

data class Store(
    val storeID: String,
    val storeName: String,
    val isActive: Int,
    val images: Images,
) {
    data class Images(
        val banner: String,
        val logo: String,
        val icon: String,
    )
}

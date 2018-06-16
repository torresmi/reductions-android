package com.fuzzyfunctors.reductions.domain.store

data class Store(
        val id: String,
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

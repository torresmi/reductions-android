package com.fuzzyfunctors.reductions.data.store

import com.fuzzyfunctors.reductions.core.store.Store as CoreStore

fun Store.toCore(): CoreStore {
    val images = images
    return CoreStore(
            id = storeID,
            name = storeName,
            isActive = isActive == 1,
            images = CoreStore.Images(
                    banner = images.banner,
                    logo = images.logo,
                    icon = images.icon
            )
    )
}

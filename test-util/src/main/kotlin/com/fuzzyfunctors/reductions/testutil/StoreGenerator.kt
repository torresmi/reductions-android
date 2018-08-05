package com.fuzzyfunctors.reductions.`test-util`

import com.fuzzyfunctors.reductions.core.store.Store
import io.kotlintest.properties.Gen

class StoreGenerator : Gen<Store> {

    override fun constants(): Iterable<Store> = emptyList()

    override fun random(): Sequence<Store> = generateSequence {
        Store(
                id = randomString(),
                name = randomString(),
                isActive = randomBool(),
                images = Store.Images(
                        banner = randomString(),
                        logo = randomString(),
                        icon = randomString()
                )
        )
    }
}

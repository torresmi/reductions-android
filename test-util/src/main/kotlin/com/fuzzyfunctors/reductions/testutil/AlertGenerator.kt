package com.fuzzyfunctors.reductions.testutil

import com.fuzzyfunctors.reductions.core.alert.Alert
import io.kotlintest.properties.Gen

class AlertGenerator : Gen<Alert> {

    override fun constants(): Iterable<Alert> = listOf(
        Alert(
            email = randomString(),
            gameID = randomString(),
            price = null
        )
    )

    override fun random(): Sequence<Alert> = generateSequence {
        Alert(
            email = randomString(),
            gameID = randomString(),
            price = NullableGenerator(Gen.string()).firstRandom()
        )
    }
}

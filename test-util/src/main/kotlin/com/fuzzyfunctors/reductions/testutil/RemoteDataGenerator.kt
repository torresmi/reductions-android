package com.fuzzyfunctors.reductions.testutil

import io.kotlintest.properties.Gen
import remotedata.RemoteData

class RemoteDataGenerator<A : Any, B : Any>(
        val errorGen: Gen<A>,
        val successGen: Gen<B>
) : Gen<RemoteData<A, B>> {

    override fun constants(): Iterable<RemoteData<A, B>> =
            listOf(
                    RemoteData.NotAsked,
                    RemoteData.Loading,
                    RemoteData.succeed(successGen.firstRandom()),
                    RemoteData.fail(errorGen.firstRandom())
            )

    override fun random(): Sequence<RemoteData<A, B>> = generateSequence {
        RemoteData.succeed(successGen.firstRandom())
    }
}

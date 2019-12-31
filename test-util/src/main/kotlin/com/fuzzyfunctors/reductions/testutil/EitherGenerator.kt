package com.fuzzyfunctors.reductions.testutil

import arrow.core.Either
import io.kotlintest.properties.Gen

class EitherGenerator<A, B>(
    val leftGen: Gen<A>,
    val rightGen: Gen<B>
) : Gen<Either<A, B>> {

    override fun constants(): Iterable<Either<A, B>> =
        listOf(
            Either.left(leftGen.firstRandom()),
            Either.right(rightGen.firstRandom())
        )

    override fun random(): Sequence<Either<A, B>> = generateSequence {
        Either.right(rightGen.firstRandom())
    }
}

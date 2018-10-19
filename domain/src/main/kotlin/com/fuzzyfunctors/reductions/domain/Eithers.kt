package com.fuzzyfunctors.reductions.domain

import arrow.core.Either
import io.reactivex.Maybe
import remotedata.RemoteData

fun <A : Any, B : Any> Either<A, B>.toRemoteData(): RemoteData<A, B> =
        fold({ RemoteData.fail(it) }, { RemoteData.succeed(it) })

fun <A : Any, B : Any> Either<A, B>.toMaybeLeft(): Maybe<A> =
        fold({ Maybe.just(it) }, { Maybe.empty() })

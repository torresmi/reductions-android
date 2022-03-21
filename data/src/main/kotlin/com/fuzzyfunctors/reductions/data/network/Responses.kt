package com.fuzzyfunctors.reductions.data.network

import arrow.core.Either
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import remotedata.RemoteData
import retrofit2.Response

fun <A> Response<A>.toEither(): Either<LoadingFailure.Remote, A> =
    if (isSuccessful) {
        Either.Right(body()!!)
    } else {
        val failure = LoadingFailure.Remote(code())
        Either.Left(failure)
    }

fun <A : Any> Response<A>.toRemoteData(): RemoteData<LoadingFailure.Remote, A> =
    if (isSuccessful) {
        RemoteData.succeed(body()!!)
    } else {
        val failure = LoadingFailure.Remote(code())
        RemoteData.fail(failure)
    }

package com.fuzzyfunctors.reductions.data

import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Flowable
import remotedata.RemoteData

data class CacheHandler<A : Any>(var cache: RemoteData<LoadingFailure, A> = RemoteData.NotAsked) {

    fun getCacheOrFetch(
            fetch: () -> Flowable<RemoteData<LoadingFailure.Remote, A>>
    ): Flowable<RemoteData<LoadingFailure, A>> =
            Flowable.just(cache)
                    .flatMap {
                        if (it.isSuccess()) {
                            Flowable.just(it)
                        } else {
                            fetch()
                        }
                    }
}

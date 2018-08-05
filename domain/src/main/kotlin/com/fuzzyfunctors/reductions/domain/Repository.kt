package com.fuzzyfunctors.reductions.domain

import io.reactivex.Flowable
import remotedata.RemoteData

interface Repository<A : Any> {

    /*
     * Get the latest data. Will get avoid another network request if already cached
     */
    fun get(): Flowable<RemoteData<LoadingFailure, A>>

    /*
     * Force fetching the data from network, skipping the cache
     */
    fun fetch(): Flowable<RemoteData<LoadingFailure.Remote, A>>

    fun save(data: A)
}

package com.fuzzyfunctors.reductions.data.store

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.data.AbsDataRepository
import com.fuzzyfunctors.reductions.data.CacheHandler
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Flowable
import io.reactivex.Single
import remotedata.RemoteData

class StoreDataRepository(
        private val networkDataSource: StoreNetworkDataSource,
        private val cacheHandler: CacheHandler<List<Store>> = CacheHandler()
) : AbsDataRepository<List<Store>>() {

    override fun get(): Flowable<RemoteData<LoadingFailure, List<Store>>> =
            cacheHandler.getCacheOrFetch { fetch() }

    override fun networkCall(): Single<Either<LoadingFailure.Remote, List<Store>>> =
            networkDataSource.getStores()

    override fun save(data: List<Store>) {
        // TODO: Add disk
        cacheHandler.cache = RemoteData.succeed(data)
    }
}

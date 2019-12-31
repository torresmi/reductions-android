package com.fuzzyfunctors.reductions.data.store

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.network.toCore
import com.fuzzyfunctors.reductions.data.network.toEither
import com.fuzzyfunctors.reductions.domain.LoadingFailure

class StoreNetworkDataSource(private val networkService: CheapSharkService) {

    suspend fun getStores(): Either<LoadingFailure.Remote, List<Store>> =
        networkService.getStores()
            .toEither()
            .map { stores ->
                stores.map { it.toCore() }
            }
}

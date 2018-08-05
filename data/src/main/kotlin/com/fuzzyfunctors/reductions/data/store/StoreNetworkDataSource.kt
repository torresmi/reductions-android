package com.fuzzyfunctors.reductions.data.store

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.network.toCore
import com.fuzzyfunctors.reductions.data.network.toEither
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Single

class StoreNetworkDataSource(private val networkService: CheapSharkService) {

    fun getStores(): Single<Either<LoadingFailure.Remote, List<Store>>> =
            networkService.getStores()
                    .map { it.toEither() }
                    .map {
                        it.map { stores ->
                            stores.map { it.toCore() }
                        }
                    }
}

package com.fuzzyfunctors.reductions.data.store

import arrow.core.Option
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.store.StoreRepository
import com.fuzzyfunctors.reductions.domain.toMaybeLeft
import io.reactivex.Maybe
import io.reactivex.Observable

class StoreDataRepository(
    private val networkDataSource: StoreNetworkDataSource,
    private val memoryStore: ReactiveStore<StoreId, Store>
) : StoreRepository {

    override fun getStore(id: StoreId): Observable<Option<Store>> = memoryStore.get(id)

    override fun getStores(): Observable<Option<Set<Store>>> = memoryStore.get()

    override fun fetchStores(): Maybe<LoadingFailure.Remote> =
            networkDataSource.getStores()
                    .doOnSuccess { response ->
                        response.fold(
                                {},
                                { memoryStore.store(it) }
                        )
                    }
                    .flatMapMaybe { it.toMaybeLeft() }
}

package com.fuzzyfunctors.reductions.data.store

import arrow.core.Option
import arrow.core.toOption
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.store.StoreRepository
import com.fuzzyfunctors.reductions.domain.toMaybeLeft
import io.reactivex.Maybe
import io.reactivex.Observable
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.rx2.asObservable

class StoreDataRepository(
    private val networkDataSource: StoreNetworkDataSource,
    private val memoryStore: ReactiveStore<StoreId, Store>
) : StoreRepository {

    override fun getStore(id: StoreId): Observable<Option<Store>> = memoryStore.get(id)
        .map { it.toOption() }
        .asObservable()

    override fun getStores(): Observable<Option<Set<Store>>> = memoryStore.get()
        .map { it.toOption() }
        .asObservable()

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

package com.fuzzyfunctors.reductions.data.store

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.store.StoreRepository
import kotlinx.coroutines.flow.Flow

class StoreDataRepository(
    private val networkDataSource: StoreNetworkDataSource,
    private val memoryStore: ReactiveStore<StoreId, Store>,
) : StoreRepository {

    override fun getStore(id: StoreId): Flow<Store?> = memoryStore.get(id)

    override fun getStores(): Flow<Set<Store>?> = memoryStore.get()

    override suspend fun fetchStores(): LoadingFailure.Remote? =
        when (val response = networkDataSource.getStores()) {
            is Either.Left -> {
                response.value
            }
            is Either.Right -> {
                memoryStore.store(response.value)
                null
            }
        }
}

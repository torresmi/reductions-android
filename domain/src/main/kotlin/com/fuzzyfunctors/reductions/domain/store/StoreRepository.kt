package com.fuzzyfunctors.reductions.domain.store

import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import kotlinx.coroutines.flow.Flow

interface StoreRepository {
    fun getStore(id: StoreId): Flow<Store?>

    fun getStores(): Flow<Set<Store>?>

    suspend fun fetchStores(): LoadingFailure.Remote?
}

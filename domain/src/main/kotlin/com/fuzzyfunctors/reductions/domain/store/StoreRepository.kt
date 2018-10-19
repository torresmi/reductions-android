package com.fuzzyfunctors.reductions.domain.store

import arrow.core.Option
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Maybe
import io.reactivex.Observable

interface StoreRepository {

    fun getStore(id: StoreId): Observable<Option<Store>>

    fun getStores(): Observable<Option<Set<Store>>>

    fun fetchStores(): Maybe<LoadingFailure.Remote>
}

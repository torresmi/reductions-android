package com.fuzzyfunctors.reductions.data

import arrow.core.Option
import io.reactivex.Observable

interface ReactiveStore<K, V> {

    fun store(item: V)

    fun store(items: Collection<V>)

    fun get(key: K): Observable<Option<V>>

    fun get(): Observable<Option<Set<V>>>

    fun clear()
}

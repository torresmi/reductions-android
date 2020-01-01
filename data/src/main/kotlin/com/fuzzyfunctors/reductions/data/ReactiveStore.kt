package com.fuzzyfunctors.reductions.data

import kotlinx.coroutines.flow.Flow

interface ReactiveStore<K, V> {

    fun store(item: V)

    fun store(items: Collection<V>)

    fun get(key: K): Flow<V?>

    fun get(): Flow<Set<V>?>

    fun clear()
}

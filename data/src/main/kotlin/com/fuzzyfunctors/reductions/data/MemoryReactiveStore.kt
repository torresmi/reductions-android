package com.fuzzyfunctors.reductions.data

import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.ConcurrentHashMap

class MemoryReactiveStore<K, V>(private inline val keyForItem: (V) -> K) : ReactiveStore<K, V> {

    private val cache = ConcurrentHashMap<K, V>()

    private val itemsChannel = BroadcastChannel<Set<V>?>(1)

    private val itemChannels = ConcurrentHashMap<K, BroadcastChannel<V?>>()

    override fun store(item: V) {
        val key = keyForItem(item)
        cache[key] = item

        getOrCreateChannel(key).trySend(item)
        itemsChannel.trySend(getAllItems())
    }

    override fun store(items: Collection<V>) {
        val keyValue = items.associateBy(keyForItem)
        cache.putAll(keyValue)
        itemsChannel.trySend(getAllItems())
        updateExistingChannels()
    }

    override fun get(key: K): Flow<V?> =
        getOrCreateChannel(key).asFlow()
            .onStart {
                val item = cache[key]
                emit(item)
            }

    override fun get(): Flow<Set<V>?> = itemsChannel.asFlow()
        .onStart { emit(getAllItems()) }

//    override fun get(): Flow<Set<V>?> = flow {
//        emit(getAllItems())
//        emitAll(itemsChannel.openSubscription())
//    }

    override fun clear() = cache.clear()

    private fun getAllItems(): Set<V>? = cache.values.toSet()
        .takeIf { cache.isNotEmpty() }

    private fun getOrCreateChannel(key: K): BroadcastChannel<V?> =
        itemChannels.getOrPut(key) { BroadcastChannel(1) }

    private fun updateExistingChannels() {
        val keys = itemChannels.keys().toList()
        keys.forEach {
            val item = cache[it]
            updateItemChannel(it, item)
        }
    }

    private fun updateItemChannel(key: K, item: V?) {
        itemChannels[key]?.trySend(item)
    }
}

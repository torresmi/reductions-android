package com.fuzzyfunctors.reductions.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import java.util.concurrent.ConcurrentHashMap

class MemoryReactiveStore<K, V>(private inline val keyForItem: (V) -> K) : ReactiveStore<K, V> {
    private val cache = ConcurrentHashMap<K, V>()

    private val itemsStateFlow = MutableStateFlow<Set<V>?>(null)

    private val itemStateFlows = ConcurrentHashMap<K, MutableStateFlow<V?>>()

    override fun store(item: V) {
        val key = keyForItem(item)
        cache[key] = item

        getOrCreateStateFlow(key).tryEmit(item)
        itemsStateFlow.tryEmit(getAllItems())
    }

    override fun store(items: Collection<V>) {
        val keyValue = items.associateBy(keyForItem)
        cache.putAll(keyValue)
        itemsStateFlow.tryEmit(getAllItems())
        updateExistingChannels()
    }

    override fun get(key: K): Flow<V?> = getOrCreateStateFlow(key)
        .onStart {
            val item = cache[key]
            emit(item)
        }

    override fun get(): Flow<Set<V>?> = itemsStateFlow
        .onStart { emit(getAllItems()) }

//    override fun get(): Flow<Set<V>?> = flow {
//        emit(getAllItems())
//        emitAll(itemsChannel.openSubscription())
//    }

    override fun clear() = cache.clear()

    private fun getAllItems(): Set<V>? = cache.values.toSet()
        .takeIf { cache.isNotEmpty() }

    private fun getOrCreateStateFlow(key: K): MutableStateFlow<V?> =
        itemStateFlows.getOrPut(key) { MutableStateFlow(null) }

    private fun updateExistingChannels() {
        val keys = itemStateFlows.keys().toList()
        keys.forEach {
            val item = cache[it]
            updateItemChannel(it, item)
        }
    }

    private fun updateItemChannel(
        key: K,
        item: V?,
    ) {
        itemStateFlows[key]?.tryEmit(item)
    }
}

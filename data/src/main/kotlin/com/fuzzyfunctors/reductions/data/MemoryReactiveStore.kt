package com.fuzzyfunctors.reductions.data

import arrow.core.Option
import arrow.core.toOption
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap

class MemoryReactiveStore<K, V>(private inline val keyForItem: (V) -> K) : ReactiveStore<K, V> {

    private val cache = ConcurrentHashMap<K, V>()

    private val itemsRelay = PublishRelay.create<Option<Set<V>>>()
            .toSerialized()

    private val itemRelays = ConcurrentHashMap<K, Relay<Option<V>>>()

    override fun store(item: V) {
        val key = keyForItem(item)
        cache[key] = item

        getOrCreatePublisher(key).accept(item.toOption())
        itemsRelay.accept(getAllItems())
    }

    @Suppress("CheckResult")
    override fun store(items: Collection<V>) {
        Observable.just(items)
                .subscribeOn(Schedulers.computation())
                .subscribe {
                    val keyValue = it.associateBy(keyForItem)
                    cache.putAll(keyValue)
                    itemsRelay.accept(getAllItems())

                    updateExistingPublishers()
                }
    }

    override fun get(key: K): Observable<Option<V>> =
            Observable
                    .defer {
                        val item = cache[key].toOption()
                        getOrCreatePublisher(key).startWith(item)
                    }

    override fun get(): Observable<Option<Set<V>>> =
            Observable
                    .defer {
                        val allItems = getAllItems()
                        itemsRelay.startWith(allItems)
                    }

    override fun clear() = cache.clear()

    private fun getAllItems(): Option<Set<V>> = cache.values.toSet()
            .toOption()
            .filterNot { it.isEmpty() }

    private fun getOrCreatePublisher(key: K): Relay<Option<V>> =
            itemRelays.getOrPut(key) {
                PublishRelay.create<Option<V>>().toSerialized()
            }

    private fun updateExistingPublishers() {
        val keys = itemRelays.keys().toList().toSet()
        keys.forEach {
            val item = cache[it].toOption()
            updateItemPublisher(it, item)
        }
    }

    private fun updateItemPublisher(key: K, item: Option<V>) {
        val publisher = itemRelays.get(key).toOption()
        publisher.fold(
                {},
                { it.accept(item) }
        )
    }
}

package com.fuzzyfunctors.reductions.data

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch

class MemoryReactiveStoreTest : DescribeSpec() {

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    init {

        val sut = MemoryReactiveStore<String, Int> { it.toString() }
        val key = "1"
        val item = 1

        val items = setOf(item)

        describe("store an item") {

            it("adds to the cache") {
                sut.store(item)

                val result = sut.get(key).first()
                result shouldBe item
            }

            it("updates a watching observer") {
                val result = waitForSubscribers(
                    subscription = {
                        sut.get(key)
                            .take(2)
                            .toList()
                    },
                    action = {
                        sut.store(item)
                    }
                )

                result shouldBe listOf(null, item)
            }

            it("updates a watching observer for all items") {
                val result = waitForSubscribers(
                    subscription = {
                        sut.get()
                            .take(2)
                            .toList()
                    },
                    action = {
                        sut.store(item)
                    }
                )

                result shouldBe listOf(null, items)
            }
        }

        describe("store items") {

            it("adds items to the cache") {
                sut.store(items)

                val result = sut.get().first()

                result shouldBe items
            }

            it("updates a watching observer") {
                val result = waitForSubscribers(
                    subscription = {
                        sut.get()
                            .take(2)
                            .toList()
                    },
                    action = {
                        sut.store(items)
                    }
                )

                result shouldBe listOf(null, items)
            }

            it("Updates individual item observers") {

                val result = waitForSubscribers(
                    subscription = {
                        sut.get(key)
                            .take(2)
                            .toList()
                    },
                    action = {
                        sut.store(item)
                    }
                )

                result shouldBe listOf(null, item)
            }
        }

        describe("item stream") {

            it("starts with null") {
                val result = sut.get(key).first()

                result shouldBe null
            }
        }

        describe("items stream") {

            it("starts with null") {
                val result = sut.get().first()

                result shouldBe null
            }
        }

        describe("clearing") {

            it("clears the cache") {
                sut.store(items)

                sut.clear()

                val result = sut.get().first()

                result shouldBe null
            }
        }
    }
}

private suspend fun <A> CoroutineScope.waitForSubscribers(
    subscription: suspend CoroutineScope.() -> A,
    action: suspend CoroutineScope.() -> Unit
): A {
    val subscriptionTask = async(block = subscription)

    // TODO: This is probably flaky, and delays the call enough to start the subscription.
    // - This will be changed later when we refactor the store to use suspension functions
    launch(block = action)

    return subscriptionTask.await()
}

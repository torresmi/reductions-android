package com.fuzzyfunctors.reductions.data

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.flow.first

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
                val initial = sut.get(key).first()

                sut.store(item)

                val updated = sut.get(key).first()

                val result = listOf(initial, updated)

                result shouldBe listOf(null, item)
            }

            it("updates a watching observer for all items") {
                val initial = sut.get().first()

                sut.store(item)

                val updated = sut.get().first()

                val result = listOf(initial, updated)

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
                val initial = sut.get().first()

                sut.store(items)

                val updated = sut.get().first()

                val result = listOf(initial, updated)

                result shouldBe listOf(null, items)
            }

            it("Updates individual item observers") {
                val initial = sut.get(key).first()

                sut.store(item)

                val updated = sut.get(key).first()

                val result = listOf(initial, updated)

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

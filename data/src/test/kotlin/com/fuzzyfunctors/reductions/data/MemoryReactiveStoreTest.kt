package com.fuzzyfunctors.reductions.data

import arrow.core.None
import arrow.core.Some
import io.kotlintest.specs.DescribeSpec
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers

class MemoryReactiveStoreTest : DescribeSpec() {

    override fun isInstancePerTest(): Boolean = true

    init {

        val sut = MemoryReactiveStore<String, Int> { it.toString() }
        val key = "1"
        val item = 1

        val items = setOf(item)

        describe("store an item") {

            it("adds to the cache") {
                sut.store(item)

                sut.get(key).test()
                    .assertValue(Some(item))
            }

            it("updates a watching observer") {
                val ts = sut.get(key).test()

                sut.store(item)

                ts.assertValueAt(1, Some(item))
            }

            it("updates a watching observer for all items") {
                val ts = sut.get().test()

                sut.store(item)

                ts.assertValueAt(1, Some(items))
            }
        }

        describe("store items") {

            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }

            it("adds items to the cache") {
                val ts = sut.get().test()

                sut.store(items)

                ts.assertValueAt(1, Some(items))
            }

            it("updates a watching observer") {
                val ts = sut.get().test()

                sut.store(items)

                ts.assertValueAt(1, Some(items))
            }

            it("Updates individual item observers") {
                val ts = sut.get(key).test()

                sut.store(item)

                ts.assertValueAt(1, Some(item))
            }
        }

        describe("item stream") {

            it("starts with nothing") {
                sut.get(key).test()
                    .assertValue(None)
            }

            it("does not terminate") {
                val ts = sut.get(key).test()

                sut.store(item)

                ts.assertNotTerminated()
            }
        }

        describe("items stream") {

            it("starts with nothing") {
                sut.get().test()
                    .assertValue(None)
            }

            it("does not terminate") {
                val ts = sut.get().test()

                sut.store(items)

                ts.assertNotTerminated()
            }
        }

        describe("clearing") {

            it("clears the cache") {
                sut.store(items)

                sut.clear()

                sut.get().test()
                    .assertValue(None)
            }
        }
    }
}

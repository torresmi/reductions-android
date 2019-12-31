package com.fuzzyfunctors.reductions.data.store

import arrow.core.Either
import arrow.core.None
import arrow.core.Some
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.data.Mocks
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.randomStore
import io.kotlintest.Description
import io.kotlintest.TestResult
import io.kotlintest.specs.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.Single

class StoreDataRepositoryTest : DescribeSpec() {

    val mockStoreNetworkDataSource = Mocks.mockStoreNetworkDataSource
    val mockMemoryReactiveStore = mockk<MemoryReactiveStore<StoreId, Store>>()

    val sut = StoreDataRepository(mockStoreNetworkDataSource, mockMemoryReactiveStore)

    override fun isInstancePerTest(): Boolean = true

    val store = randomStore()

    init {

        describe("fetching stores") {

            context("fetch was successful") {

                val responseValue = listOf(store)
                every { mockStoreNetworkDataSource.getStores() } returns
                    Single.just(Either.right(responseValue) as Either<LoadingFailure.Remote, List<Store>>)

                it("makes the network call") {
                    sut.fetchStores().test()

                    verify(exactly = 1) { mockStoreNetworkDataSource.getStores() }
                }

                it("updates the memory cache") {
                    sut.fetchStores().test()

                    verify(exactly = 1) { mockMemoryReactiveStore.store(responseValue) }
                }
            }

            context("fetch was a failure") {

                val statusCode = 404
                val responseValue = LoadingFailure.Remote(statusCode)
                every { mockStoreNetworkDataSource.getStores() } returns
                    Single.just(Either.left(responseValue) as Either<LoadingFailure.Remote, List<Store>>)

                it("makes the network call") {
                    sut.fetchStores().test()

                    verify(exactly = 1) { mockStoreNetworkDataSource.getStores() }
                }

                it("does not update the cache") {
                    sut.fetchStores().test()

                    verify(exactly = 0) { mockMemoryReactiveStore.store(any<Collection<Store>>()) }
                }
            }
        }

        describe("getting a store") {

            val storeId = store.id

            context("the store exists") {

                every { mockMemoryReactiveStore.get(storeId) } returns
                    Observable.just(Some(store))

                it("should return the store") {
                    sut.getStore(storeId).test()
                        .assertValue(Some(store))
                }
            }

            context("the store does not exist") {

                every { mockMemoryReactiveStore.get(any()) } returns
                    Observable.just(None)

                it("should return nothing") {
                    sut.getStore(storeId).test()
                        .assertValue(None)
                }
            }
        }

        describe("getting all stores") {

            context("stores exists") {

                val stores = setOf(store)

                every { mockMemoryReactiveStore.get() } returns
                    Observable.just(Some(stores))

                it("should return the store") {
                    sut.getStores().test()
                        .assertValue(Some(stores))
                }
            }

            context("haven't found any stores yet") {

                every { mockMemoryReactiveStore.get() } returns
                    Observable.just(None)

                it("should return nothing") {
                    sut.getStores().test()
                        .assertValue(None)
                }
            }
        }
    }

    override fun afterTest(description: Description, result: TestResult) {
        super.afterTest(description, result)
        clearMocks(mockStoreNetworkDataSource, mockMemoryReactiveStore)
    }
}

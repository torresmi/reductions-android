package com.fuzzyfunctors.reductions.data.store

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.randomStore
import io.kotlintest.IsolationMode
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single

class StoreDataRepositoryTest : DescribeSpec() {

    val mockStoreNetworkDataSource = mockk<StoreNetworkDataSource>()
    val mockMemoryReactiveStore = mockk<MemoryReactiveStore<StoreId, Store>>(relaxUnitFun = true)

    val sut = StoreDataRepository(mockStoreNetworkDataSource, mockMemoryReactiveStore)

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    val store = randomStore()

    init {
        describe("fetching stores") {

            context("fetch was successful") {

                val responseValue = listOf(store)
                coEvery { mockStoreNetworkDataSource.getStores() } returns Either.right(responseValue)

                it("makes the network call") {
                    sut.fetchStores()

                    coVerify(exactly = 1) { mockStoreNetworkDataSource.getStores() }
                }

                it("updates the memory cache") {
                    sut.fetchStores()

                    verify(exactly = 1) { mockMemoryReactiveStore.store(responseValue) }
                }
            }

            context("fetch was a failure") {

                val statusCode = 404
                val responseValue = LoadingFailure.Remote(statusCode)
                coEvery { mockStoreNetworkDataSource.getStores() } returns Either.left(responseValue)

                it("makes the network call") {
                    sut.fetchStores()

                    coVerify(exactly = 1) { mockStoreNetworkDataSource.getStores() }
                }

                it("does not update the cache") {
                    sut.fetchStores()

                    verify(exactly = 0) { mockMemoryReactiveStore.store(any<Collection<Store>>()) }
                }
            }
        }

        describe("getting a store") {

            val storeId = store.id

            context("the store exists") {

                every { mockMemoryReactiveStore.get(storeId) } returns flowOf(store)

                it("should return the store") {
                    val result = sut.getStore(storeId).single()

                    result shouldBe store
                }
            }

            context("the store does not exist") {

                every { mockMemoryReactiveStore.get(any()) } returns flowOf(null)

                it("should return null") {
                    val result = sut.getStore(storeId).single()

                    result shouldBe null
                }
            }
        }

        describe("getting all stores") {

            context("stores exists") {

                val stores = setOf(store)

                every { mockMemoryReactiveStore.get() } returns flowOf(stores)

                it("should return the store") {
                    val result = sut.getStores().single()

                    result shouldBe stores
                }
            }

            context("haven't found any stores yet") {

                every { mockMemoryReactiveStore.get() } returns flowOf(null)

                it("should return null") {
                    val result = sut.getStores().single()

                    result shouldBe null
                }
            }
        }
    }
}

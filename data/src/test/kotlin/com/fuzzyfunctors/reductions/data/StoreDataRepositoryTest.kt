package com.fuzzyfunctors.reductions.data

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.data.store.StoreDataRepository
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.randomStore
import io.kotlintest.Description
import io.kotlintest.TestResult
import io.kotlintest.specs.DescribeSpec
import io.mockk.clearMocks
import io.mockk.every
import io.mockk.verify
import io.reactivex.Single
import io.reactivex.subscribers.TestSubscriber
import remotedata.RemoteData

class StoreDataRepositoryTest : DescribeSpec() {

    val mockStoreNetworkDataSource = Mocks.mockStoreNetworkDataSource

    val sut = StoreDataRepository(mockStoreNetworkDataSource)

    override fun isInstancePerTest(): Boolean = true

    val store = randomStore()

    var ts = TestSubscriber.create<RemoteData<LoadingFailure, List<Store>>>()

    init {

        describe("fetching stores") {

            context("fetch was successful") {

                val responseValue = listOf(store)
                every { mockStoreNetworkDataSource.getStores() } returns
                        Single.just(Either.right(responseValue) as Either<LoadingFailure.Remote, List<Store>>)

                it("updates the cache") {

                    sut.fetch().subscribe(ts)

                    ts = TestSubscriber.create()
                    sut.get().subscribe(ts)

                    verify(exactly = 1) { mockStoreNetworkDataSource.getStores() }
                }
            }

            context("fetch was a failure") {

                val responseValue = LoadingFailure.Remote(0)
                every { mockStoreNetworkDataSource.getStores() } returns
                        Single.just(Either.left(responseValue) as Either<LoadingFailure.Remote, List<Store>>)

                it("does not update the cache") {

                    sut.fetch().subscribe(ts)

                    ts = TestSubscriber.create()
                    sut.get().subscribe(ts)

                    verify(exactly = 2) { mockStoreNetworkDataSource.getStores() }
                }
            }

        }

    }

    override fun afterTest(description: Description, result: TestResult) {
        super.afterTest(description, result)
        clearMocks(mockStoreNetworkDataSource)
    }
}

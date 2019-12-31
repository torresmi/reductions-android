package com.fuzzyfunctors.reductions.data.deal

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toOption
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.data.Mocks
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.DealGenerator
import com.fuzzyfunctors.reductions.testutil.DealInfoGenerator
import com.fuzzyfunctors.reductions.testutil.firstRandom
import io.kotlintest.data.forall
import io.kotlintest.specs.DescribeSpec
import io.kotlintest.tables.row
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import java.net.HttpURLConnection

class DealDataRepositoryTest : DescribeSpec() {

    override fun isInstancePerTest(): Boolean = true

    val mockDealNetworkDataSource = Mocks.mockDealNetworkDataSource
    val mockDealsReactiveStore = mockk<MemoryReactiveStore<DealType, DealTypeData>>(relaxed = true)
    val mockDealInfoReactiveStore = mockk<MemoryReactiveStore<DealId, DealInfo>>()

    val sut = DealDataRepository(mockDealNetworkDataSource, mockDealsReactiveStore, mockDealInfoReactiveStore)

    init {

        describe("fetching deals") {
            val deals = DealGenerator().random().take(3).toList()
            val fetches = lazy {
                arrayOf(
                    row(sut.fetchTopDeals()),
                    row(sut.fetchLatestDeals()),
                    row(sut.fetchMostSavingsDeals()),
                    row(sut.fetchNewestGamesDeals())
                )
            }

            context("successful response") {

                every { mockDealNetworkDataSource.getDeals(any()) } returns
                    Single.just(Either.right(deals))

                it("returns no errors") {
                    forall(*fetches.value) { deals: Maybe<LoadingFailure.Remote> ->
                        deals.test()
                            .await()
                            .assertNoValues()
                    }
                }

                it("updates the memory store with the new deals") {
                    sut.fetchTopDeals().test().awaitTerminalEvent()

                    verify { mockDealsReactiveStore.store(DealTypeData(DealType.TOP, deals)) }
                }
            }

            context("failure response") {

                val error = LoadingFailure.Remote(HttpURLConnection.HTTP_NOT_FOUND)
                every { mockDealNetworkDataSource.getDeals(any()) } returns
                    Single.just(Either.left(error))

                it("returns an api error") {
                    forall(*fetches.value) { deals: Maybe<LoadingFailure.Remote> ->
                        deals.test()
                            .await()
                            .assertValue(error)
                    }
                }

                val ioError = RuntimeException()
                every { mockDealNetworkDataSource.getDeals(any()) } returns
                    Single.error(ioError)

                it("returns an io error") {
                    forall(*fetches.value) { deals: Maybe<LoadingFailure.Remote> ->
                        deals.test()
                            .await()
                            .assertError(ioError)
                    }
                }
            }
        }

        describe("fetching deal") {
            val deal = DealInfoGenerator().firstRandom()

            context("successful response") {

                every { mockDealNetworkDataSource.getDeal(any()) } returns
                    Single.just(Either.right(deal))

                it("returns no errors") {
                    sut.fetchDealInfo(deal.id).test()
                        .await()
                        .assertNoValues()
                }

                it("updates the memory store with the new deals") {
                    sut.fetchDealInfo(deal.id).test().awaitTerminalEvent()

                    verify { mockDealInfoReactiveStore.store(deal) }
                }
            }

            context("failure response") {

                val error = LoadingFailure.Remote(HttpURLConnection.HTTP_NOT_FOUND)
                every { mockDealNetworkDataSource.getDeal(any()) } returns
                    Single.just(Either.left(error))

                it("returns an api error") {
                    sut.fetchDealInfo(deal.id).test()
                        .await()
                        .assertValue(error)
                }

                val ioError = RuntimeException()
                every { mockDealNetworkDataSource.getDeal(any()) } returns
                    Single.error(ioError)

                it("returns an io error") {
                    sut.fetchDealInfo(deal.id).test()
                        .await()
                        .assertError(ioError)
                }
            }
        }

        describe("getting deals") {
            val deals = DealGenerator().random().take(3).toList()
            val getRequests = lazy {
                arrayOf(
                    row(sut.getTopDeals()),
                    row(sut.getLatestDeals()),
                    row(sut.getMostSavingsDeals()),
                    row(sut.getNewestGamesDeals())
                )
            }

            context("deals exist") {

                every { mockDealsReactiveStore.get(any()) } returns
                    Observable.just(DealTypeData(DealType.TOP, deals).toOption())

                it("returns deals") {
                    forall(*getRequests.value) { request: Observable<Option<List<Deal>>> ->
                        request.test()
                            .assertValue(Some(deals))
                    }
                }
            }

            context("deals do not exist") {

                every { mockDealsReactiveStore.get(any()) } returns
                    Observable.just(None)

                it("should return nothing") {
                    forall(*getRequests.value) { request: Observable<Option<List<Deal>>> ->
                        request.test()
                            .assertValue(None)
                    }
                }
            }
        }
    }
}

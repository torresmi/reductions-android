package com.fuzzyfunctors.reductions.data.deal

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.DealGenerator
import com.fuzzyfunctors.reductions.testutil.DealInfoGenerator
import com.fuzzyfunctors.reductions.testutil.firstRandom
import io.kotlintest.IsolationMode
import io.kotlintest.properties.Gen
import io.kotlintest.properties.assertAll
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.net.HttpURLConnection
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest

class DealDataRepositoryTest : DescribeSpec() {

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    val mockDealNetworkDataSource = mockk<DealNetworkDataSource>()
    val mockDealsReactiveStore = mockk<MemoryReactiveStore<DealType, DealTypeData>>(relaxUnitFun = true)
    val mockDealInfoReactiveStore = mockk<MemoryReactiveStore<DealId, DealInfo>>(relaxUnitFun = true)

    val sut = DealDataRepository(mockDealNetworkDataSource, mockDealsReactiveStore, mockDealInfoReactiveStore)

    init {
        val dealsGenerator = Gen.list(DealGenerator())

        describe("fetching deals") {

            context("successful response") {

                val deals = dealsGenerator.firstRandom()

                coEvery { mockDealNetworkDataSource.getDeals(any()) } returns Either.right(deals)

                it("returns no errors") {
                    sut.fetchTopDeals() shouldBe null
                    sut.fetchLatestDeals() shouldBe null
                    sut.fetchMostSavingsDeals() shouldBe null
                    sut.fetchNewestGamesDeals() shouldBe null
                }

                it("updates the memory store with the new deals") {
                    sut.fetchTopDeals()

                    verify { mockDealsReactiveStore.store(DealTypeData(DealType.TOP, deals)) }
                }
            }

            context("failure response") {

                val error = LoadingFailure.Remote(HttpURLConnection.HTTP_NOT_FOUND)
                coEvery { mockDealNetworkDataSource.getDeals(any()) } returns Either.left(error)

                it("returns an api error") {
                    sut.fetchTopDeals() shouldBe error
                    sut.fetchLatestDeals() shouldBe error
                    sut.fetchMostSavingsDeals() shouldBe error
                    sut.fetchNewestGamesDeals() shouldBe error
                }
            }
        }

        describe("fetching deal") {
            val deal = DealInfoGenerator().firstRandom()

            context("successful response") {

                coEvery { mockDealNetworkDataSource.getDeal(any()) } returns Either.right(deal)

                it("updates the memory store with the new deals") {
                    sut.fetchDealInfo(deal.id)

                    verify { mockDealInfoReactiveStore.store(deal) }
                }
            }

            context("failure response") {

                val error = LoadingFailure.Remote(HttpURLConnection.HTTP_NOT_FOUND)
                coEvery { mockDealNetworkDataSource.getDeal(any()) } returns Either.left(error)

                it("returns an api error") {
                    sut.fetchDealInfo(deal.id) shouldBe error
                }
            }
        }

        describe("getting deals") {

            context("deals exist") {

                it("returns deals") {
                    assertAll(dealsGenerator) { deals ->
                        every { mockDealsReactiveStore.get(any()) } returns
                            flowOf(DealTypeData(DealType.TOP, deals))

                        runBlockingTest {
                            sut.getTopDeals().first() shouldBe deals
                        }
                    }
                }
            }

            context("deals do not exist") {

                every { mockDealsReactiveStore.get(any()) } returns flowOf(null)

                it("should return nothing") {
                    sut.getTopDeals().first() shouldBe null
                    sut.getLatestDeals().first() shouldBe null
                    sut.getMostSavingsDeals().first() shouldBe null
                    sut.getNewestGamesDeals().first() shouldBe null
                }
            }
        }
    }
}

package com.fuzzyfunctors.reductions.data.alert

import arrow.core.Either
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.kotlintest.IsolationMode
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import java.net.HttpURLConnection

class AlertDataRepositoryTest : DescribeSpec() {

    val mockAlertNetworkDataSource = mockk<AlertNetworkDataSource>()

    val sut = AlertDataRepository(mockAlertNetworkDataSource)

    private val gameId = "gameId"
    private val email = "email"
    private val price = 2.00

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    init {

        describe("watch game") {

            it("makes the call to the network") {
                mockWatch(Either.right(Unit))

                sut.watchGame(gameId, email, price)

                coVerify(exactly = 1) { mockAlertNetworkDataSource.watchGame(gameId, email, price) }
            }

            context("successful watch") {

                mockWatch(Either.right(Unit))

                it("returns no errors") {
                    sut.watchGame(gameId, email, price) shouldBe null
                }
            }

            context("failure watching") {

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockWatch(Either.left(remoteError))

                it("returns network error") {
                    sut.watchGame(gameId, email, price) shouldBe remoteError
                }
            }
        }

        describe("unwatch game") {

            mockUnwatch(Either.right(Unit))

            it("makes the call to the network") {

                sut.unwatchGame(gameId, email)

                coVerify(exactly = 1) { mockAlertNetworkDataSource.unwatchGame(gameId, email) }
            }

            context("successful unwatch") {

                it("returns no errors") {
                    sut.unwatchGame(gameId, email) shouldBe null
                }
            }

            context("failure watching") {

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockUnwatch(Either.left(remoteError))

                it("returns network error") {
                    sut.unwatchGame(gameId, email) shouldBe remoteError
                }
            }
        }
    }

    private fun mockWatch(returnValue: Either<LoadingFailure.Remote, Unit>) {
        coEvery { mockAlertNetworkDataSource.watchGame(any(), any(), any()) } returns
            returnValue
    }

    private fun mockUnwatch(returnValue: Either<LoadingFailure.Remote, Unit>) {
        coEvery { mockAlertNetworkDataSource.unwatchGame(any(), any()) } returns
            returnValue
    }
}

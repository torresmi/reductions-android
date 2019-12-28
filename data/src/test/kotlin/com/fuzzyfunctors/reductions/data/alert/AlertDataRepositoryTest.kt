package com.fuzzyfunctors.reductions.data.alert

import arrow.core.Either
import com.fuzzyfunctors.reductions.data.Mocks
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.kotlintest.specs.DescribeSpec
import io.mockk.every
import io.mockk.verify
import io.reactivex.Single
import java.net.HttpURLConnection

class AlertDataRepositoryTest : DescribeSpec() {

    val mockAlertNetworkDataSource = Mocks.mockAlertNetworkDataSource

    val sut = AlertDataRepository(mockAlertNetworkDataSource)

    private val gameId = "gameId"
    private val email = "email"
    private val price = 2.00

    override fun isInstancePerTest(): Boolean = true

    init {

        describe("watch game") {

            it("makes the call to the network") {
                mockWatch(Either.right(Unit))

                sut.watchGame(gameId, email, price).test()

                verify(exactly = 1) { mockAlertNetworkDataSource.watchGame(gameId, email, price) }
            }

            context("successful watch") {

                mockWatch(Either.right(Unit))

                it("returns no errors") {
                    sut.watchGame(gameId, email, price).test()
                            .assertNoValues()
                            .assertNoErrors()
                }
            }

            context("failure watching") {

                val error = RuntimeException()
                every { mockAlertNetworkDataSource.watchGame(any(), any(), any()) } returns
                        Single.error(error)

                it("returns onError for io error") {
                    sut.watchGame(gameId, email, price).test()
                            .assertError(error)
                }

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockWatch(Either.left(remoteError))

                it("returns network error") {
                    sut.watchGame(gameId, email, price).test()
                            .assertValue(remoteError)
                }
            }
        }

        describe("unwatch game") {

            it("makes the call to the network") {
                mockUnwatch(Either.right(Unit))

                sut.unwatchGame(gameId, email).test()

                verify(exactly = 1) { mockAlertNetworkDataSource.unwatchGame(gameId, email) }
            }

            context("successful unwatch") {

                it("returns no errors") {
                    sut.unwatchGame(gameId, email).test()
                            .assertNoValues()
                            .assertNoErrors()
                }
            }

            context("failure watching") {

                val error = RuntimeException()
                every { mockAlertNetworkDataSource.unwatchGame(any(), any()) } returns
                        Single.error(error)

                it("returns onError for io error") {
                    sut.unwatchGame(gameId, email).test()
                            .assertError(error)
                }

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockUnwatch(Either.left(remoteError))

                it("returns network error") {
                    sut.unwatchGame(gameId, email).test()
                            .assertValue(remoteError)
                }
            }
        }
    }

    private fun mockWatch(returnValue: Either<LoadingFailure.Remote, Unit>) {
        every { mockAlertNetworkDataSource.watchGame(any(), any(), any()) } returns
                Single.just(returnValue)
    }

    private fun mockUnwatch(returnValue: Either<LoadingFailure.Remote, Unit>) {
        every { mockAlertNetworkDataSource.unwatchGame(any(), any()) } returns
                Single.just(returnValue)
    }
}

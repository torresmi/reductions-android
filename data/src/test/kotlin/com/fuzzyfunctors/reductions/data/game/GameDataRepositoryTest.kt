package com.fuzzyfunctors.reductions.data.game

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.data.Mocks
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.GameBestDealGenerator
import com.fuzzyfunctors.reductions.testutil.GameGenerator
import com.fuzzyfunctors.reductions.testutil.firstRandom
import io.kotlintest.specs.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Single
import java.net.HttpURLConnection
import kotlinx.coroutines.flow.flowOf

class GameDataRepositoryTest : DescribeSpec() {

    val mockGameNetworkDataSource = Mocks.mockGameNetworkDataSource
    val mockMemoryReactiveStore = mockk<MemoryReactiveStore<GameId, Game>>(relaxed = true)

    val sut = GameDataRepository(mockGameNetworkDataSource, mockMemoryReactiveStore)

    val game = GameGenerator().firstRandom()
    val gameId = game.id

    override fun isInstancePerTest(): Boolean = true

    init {

        describe("fetching a game") {

            mockFetchGame(Either.right(game))

            it("makes the network call") {
                sut.fetchGame(gameId).test()

                verify(exactly = 1) { mockGameNetworkDataSource.getGameInfo(gameId) }
            }

            context("fetch was successful") {

                it("updates the memory cache") {
                    sut.fetchGame(gameId).test()

                    verify { mockMemoryReactiveStore.store(game) }
                }

                it("does not return any errors") {
                    sut.fetchGame(gameId).test()
                        .assertNoValues()
                        .assertNoErrors()
                }
            }

            context("fetch was a failure") {

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockFetchGame(Either.left(remoteError))

                it("does not update the cache") {
                    sut.fetchGame(gameId).test()

                    verify(exactly = 0) { mockMemoryReactiveStore.store(game) }
                }

                it("returns an error") {
                    sut.fetchGame(gameId).test()
                        .assertValue(remoteError)
                }

                val error = RuntimeException()
                every { mockGameNetworkDataSource.getGameInfo(any()) } returns
                    Single.error(error)

                it("calls onError with an io error") {
                    sut.fetchGame(gameId).test()
                        .assertError(error)
                }
            }
        }

        describe("getting a game") {

            context("the game exists") {

                mockStoreGet(Some(game))

                it("should return the game") {
                    sut.getGame(gameId).test()
                        .assertValue(Some(game))
                }
            }

            context("the game does not exist") {

                mockStoreGet(None)

                it("should return nothing") {
                    sut.getGame(gameId).test()
                        .assertValue(None)
                }
            }
        }

        describe("searching games") {
            val results = GameBestDealGenerator().random().take(3).toList()
            val title = "title"
            val limit = 1
            val exact = false
            val steamAppId = null

            context("the search was a success") {

                mockSearchGames(Either.right(results))

                it("returns the results") {
                    sut.searchGames(title, steamAppId, limit, exact).test()
                        .assertValue(Either.right(results))
                }
            }

            context("the search was a failure") {

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockSearchGames(Either.left(remoteError))

                it("returns the error response") {
                    sut.searchGames(title, steamAppId, limit, exact).test()
                        .assertValue(Either.left(remoteError))
                }

                val error = java.lang.RuntimeException()
                every { mockGameNetworkDataSource.searchGames(any(), any(), any(), any()) } returns
                    Single.error(error)

                it("calls onError with an io error") {
                    sut.searchGames(title, steamAppId, limit, exact).test()
                        .assertError(error)
                }
            }
        }
    }

    private fun mockFetchGame(response: Either<LoadingFailure.Remote, Game>) {
        every { mockGameNetworkDataSource.getGameInfo(any()) } returns
            Single.just(response)
    }

    private fun mockStoreGet(response: Option<Game>) {
        every { mockMemoryReactiveStore.get(any()) } returns flowOf(response.orNull())
    }

    private fun mockSearchGames(response: Either<LoadingFailure.Remote, List<GameBestDeal>>) {
        every { mockGameNetworkDataSource.searchGames(any(), any(), any(), any()) } returns
            Single.just(response)
    }
}

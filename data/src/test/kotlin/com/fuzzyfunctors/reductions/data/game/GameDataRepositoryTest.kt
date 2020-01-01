package com.fuzzyfunctors.reductions.data.game

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.testutil.GameBestDealGenerator
import com.fuzzyfunctors.reductions.testutil.GameGenerator
import com.fuzzyfunctors.reductions.testutil.firstRandom
import io.kotlintest.IsolationMode
import io.kotlintest.shouldBe
import io.kotlintest.specs.DescribeSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import java.net.HttpURLConnection
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf

class GameDataRepositoryTest : DescribeSpec() {

    val mockGameNetworkDataSource = mockk<GameNetworkDataSource>()
    val mockMemoryReactiveStore = mockk<MemoryReactiveStore<GameId, Game>>(relaxed = true)

    val sut = GameDataRepository(mockGameNetworkDataSource, mockMemoryReactiveStore)

    val game = GameGenerator().firstRandom()
    val gameId = game.id

    override fun isolationMode(): IsolationMode? = IsolationMode.InstancePerLeaf

    init {

        describe("fetching a game") {

            mockFetchGame(Either.right(game))

            it("makes the network call") {
                sut.fetchGame(gameId)

                coVerify(exactly = 1) { mockGameNetworkDataSource.getGameInfo(gameId) }
            }

            context("fetch was successful") {

                it("updates the memory cache") {
                    sut.fetchGame(gameId)

                    verify { mockMemoryReactiveStore.store(game) }
                }
            }

            context("fetch was a failure") {

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockFetchGame(Either.left(remoteError))

                it("does not update the cache") {
                    sut.fetchGame(gameId)

                    verify(exactly = 0) { mockMemoryReactiveStore.store(game) }
                }

                it("returns an error") {
                    sut.fetchGame(gameId) shouldBe remoteError
                }
            }
        }

        describe("getting a game") {

            context("the game exists") {

                mockStoreGet(game)

                it("should return the game") {
                    sut.getGame(gameId).first() shouldBe game
                }
            }

            context("the game does not exist") {

                mockStoreGet(null)

                it("should return nothing") {
                    sut.getGame(gameId).first() shouldBe null
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
                    sut.searchGames(title, steamAppId, limit, exact) shouldBe Either.right(results)
                }
            }

            context("the search was a failure") {

                val remoteError = LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR)
                mockSearchGames(Either.left(remoteError))

                it("returns the error response") {
                    sut.searchGames(title, steamAppId, limit, exact) shouldBe Either.left(remoteError)
                }
            }
        }
    }

    private fun mockFetchGame(response: Either<LoadingFailure.Remote, Game>) {
        coEvery { mockGameNetworkDataSource.getGameInfo(any()) } returns response
    }

    private fun mockStoreGet(response: Game?) {
        coEvery { mockMemoryReactiveStore.get(any()) } returns flowOf(response)
    }

    private fun mockSearchGames(response: Either<LoadingFailure.Remote, List<GameBestDeal>>) {
        coEvery { mockGameNetworkDataSource.searchGames(any(), any(), any(), any()) } returns
            response
    }
}

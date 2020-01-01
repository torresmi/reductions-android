package com.fuzzyfunctors.reductions.data.game

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.network.toCore
import com.fuzzyfunctors.reductions.data.network.toCoreGame
import com.fuzzyfunctors.reductions.data.network.toEither
import com.fuzzyfunctors.reductions.domain.LoadingFailure

class GameNetworkDataSource(private val networkService: CheapSharkService) {

    suspend fun getGameInfo(gameId: GameId): Either<LoadingFailure.Remote, Game> =
        networkService.getGame(gameId)
            .toEither()
            .map { it.toCoreGame(gameId) }

    suspend fun searchGames(
        title: String?,
        steamAppId: String?,
        limit: Int?,
        exact: Boolean?
    ): Either<LoadingFailure.Remote, List<GameBestDeal>> =
        networkService.searchGames(title, steamAppId?.toInt(), limit, exact)
            .toEither()
            .map { results ->
                results.map { it.toCore() }
            }
}

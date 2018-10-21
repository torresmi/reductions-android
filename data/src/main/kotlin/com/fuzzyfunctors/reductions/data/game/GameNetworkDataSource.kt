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
import io.reactivex.Single

class GameNetworkDataSource(private val networkService: CheapSharkService) {

    fun getGameInfo(gameId: GameId): Single<Either<LoadingFailure.Remote, Game>> =
            networkService.getGame(gameId)
                    .map { it.toEither() }
                    .map {
                        it.map { game ->
                            game.toCoreGame(gameId)
                        }
                    }

    fun searchGames(
            title: String?,
            steamAppId: String?,
            limit: Int?,
            exact: Boolean?
    ): Single<Either<LoadingFailure.Remote, List<GameBestDeal>>> {
        return networkService.searchGames(title, steamAppId?.toInt(), limit, exact)
                .map { it.toEither() }
                .map { response ->
                    response.map { results ->
                        results.map { it.toCore() }
                    }
                }
    }
}

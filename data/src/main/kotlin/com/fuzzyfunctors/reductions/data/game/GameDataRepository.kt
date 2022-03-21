package com.fuzzyfunctors.reductions.data.game

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.game.GameRepository
import kotlinx.coroutines.flow.Flow

class GameDataRepository(
    private val networkDataSource: GameNetworkDataSource,
    private val memoryStore: ReactiveStore<GameId, Game>
) : GameRepository {

    override fun getGame(id: GameId): Flow<Game?> = memoryStore.get(id)

    override suspend fun fetchGame(id: GameId): LoadingFailure.Remote? =
        when (val response = networkDataSource.getGameInfo(id)) {
            is Either.Left -> {
                response.value
            }
            is Either.Right -> {
                memoryStore.store(response.value)
                null
            }
        }

    override suspend fun searchGames(
        title: String?,
        steamAppId: String?,
        limit: Int?,
        exact: Boolean
    ): Either<LoadingFailure.Remote, List<GameBestDeal>> =
        networkDataSource.searchGames(title, steamAppId, limit, exact)
}

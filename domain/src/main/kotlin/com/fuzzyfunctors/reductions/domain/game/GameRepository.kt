package com.fuzzyfunctors.reductions.domain.game

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import kotlinx.coroutines.flow.Flow

interface GameRepository {

    fun getGame(id: GameId): Flow<Game?>

    suspend fun fetchGame(id: GameId): LoadingFailure.Remote?

    suspend fun searchGames(
        title: String?,
        steamAppId: String? = null,
        limit: Int? = null,
        exact: Boolean = false,
    ): Either<LoadingFailure.Remote, List<GameBestDeal>>
}

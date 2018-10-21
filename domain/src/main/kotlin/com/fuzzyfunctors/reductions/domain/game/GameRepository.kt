package com.fuzzyfunctors.reductions.domain.game

import arrow.core.Either
import arrow.core.Option
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

interface GameRepository {

    fun getGame(id: GameId): Observable<Option<Game>>

    fun fetchGame(id: GameId): Maybe<LoadingFailure.Remote>

    fun searchGames(
            title: String?,
            steamAppId: String? = null,
            limit: Int? = null,
            exact: Boolean = false
    ): Single<Either<LoadingFailure.Remote, List<GameBestDeal>>>
}

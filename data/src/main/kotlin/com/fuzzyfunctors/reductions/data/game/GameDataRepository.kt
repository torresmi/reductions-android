package com.fuzzyfunctors.reductions.data.game

import arrow.core.Either
import arrow.core.Option
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameBestDeal
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.game.GameRepository
import com.fuzzyfunctors.reductions.domain.toMaybeLeft
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

class GameDataRepository(
    private val networkDataSource: GameNetworkDataSource,
    private val memoryStore: ReactiveStore<GameId, Game>
) : GameRepository {

    override fun getGame(id: GameId): Observable<Option<Game>> = memoryStore.get(id)

    override fun fetchGame(id: GameId): Maybe<LoadingFailure.Remote> =
            networkDataSource.getGameInfo(id)
                    .doOnSuccess { response ->
                        response.fold(
                                {},
                                { memoryStore.store(it) }
                        )
                    }
                    .flatMapMaybe { it.toMaybeLeft() }

    override fun searchGames(
        title: String?,
        steamAppId: String?,
        limit: Int?,
        exact: Boolean
    ): Single<Either<LoadingFailure.Remote, List<GameBestDeal>>> =
            networkDataSource.searchGames(title, steamAppId, limit, exact)
}

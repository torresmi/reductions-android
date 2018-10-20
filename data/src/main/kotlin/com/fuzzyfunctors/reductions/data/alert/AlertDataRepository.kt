package com.fuzzyfunctors.reductions.data.alert

import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.alert.AlertRepository
import com.fuzzyfunctors.reductions.domain.toMaybeLeft
import io.reactivex.Maybe

class AlertDataRepository(private val networkDataSource: AlertNetworkDataSource) : AlertRepository {

    override fun watchGame(gameId: GameId, email: String, price: Double?): Maybe<LoadingFailure.Remote> {
        return networkDataSource.watchGame(gameId, email, price)
                .flatMapMaybe { it.toMaybeLeft() }
    }

    override fun unwatchGame(gameId: GameId, email: String): Maybe<LoadingFailure.Remote> {
        return networkDataSource.unwatchGame(gameId, email)
                .flatMapMaybe { it.toMaybeLeft() }
    }
}

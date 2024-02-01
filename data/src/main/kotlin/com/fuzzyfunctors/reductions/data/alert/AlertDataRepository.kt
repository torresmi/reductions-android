package com.fuzzyfunctors.reductions.data.alert

import arrow.core.orNull
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.alert.AlertRepository

class AlertDataRepository(private val networkDataSource: AlertNetworkDataSource) : AlertRepository {
    override suspend fun watchGame(
        gameId: GameId,
        email: String,
        price: Double?,
    ): LoadingFailure.Remote? {
        return networkDataSource.watchGame(gameId, email, price)
            .swap()
            .orNull()
    }

    override suspend fun unwatchGame(
        gameId: GameId,
        email: String,
    ): LoadingFailure.Remote? {
        return networkDataSource.unwatchGame(gameId, email)
            .swap()
            .orNull()
    }
}

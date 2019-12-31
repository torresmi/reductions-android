package com.fuzzyfunctors.reductions.domain.alert

import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.domain.LoadingFailure

interface AlertRepository {

    suspend fun watchGame(gameId: GameId, email: String, price: Double?): LoadingFailure.Remote?

    suspend fun unwatchGame(gameId: GameId, email: String): LoadingFailure.Remote?
}

package com.fuzzyfunctors.reductions.domain.alert

import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Maybe

interface AlertRepository {

    fun watchGame(gameId: GameId, email: String, price: Double?): Maybe<LoadingFailure.Remote>

    fun unwatchGame(gameId: GameId, email: String): Maybe<LoadingFailure.Remote>
}

package com.fuzzyfunctors.reductions.data.alert

import arrow.core.Either
import arrow.core.flatMap
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.network.toEither
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import retrofit2.Response
import java.net.HttpURLConnection

class AlertNetworkDataSource(private val networkService: CheapSharkService) {

    suspend fun watchGame(
        gameId: GameId,
        email: String,
        price: Double?
    ): Either<LoadingFailure.Remote, Unit> =
        networkService
            .updateAlert(
                ACTION_SET,
                email,
                gameId,
                price
            )
            .run(responseMapper)

    suspend fun unwatchGame(gameId: GameId, email: String): Either<LoadingFailure.Remote, Unit> =
        networkService
            .updateAlert(
                ACTION_DELETE,
                email,
                gameId,
                null
            )
            .run(responseMapper)

    private val responseMapper = { response: Response<Boolean> ->
        response.toEither().flatMap { isSuccess ->
            if (isSuccess) {
                Either.right(Unit)
            } else {
                Either.left(LoadingFailure.Remote(HttpURLConnection.HTTP_INTERNAL_ERROR))
            }
        }
    }

    companion object {
        private const val ACTION_SET = "set"
        private const val ACTION_DELETE = "delete"
    }
}

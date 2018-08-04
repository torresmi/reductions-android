package com.fuzzyfunctors.reductions.data.game

import com.fuzzyfunctors.reductions.domain.game.GameId
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GameService {

    @GET("games")
    fun getGames(
            @Query("title") title: String? = null,
            @Query("steamAppID") steamAppId: Int? = null,
            @Query("limit") limit: Int? = null,
            @Query("exact") exact: Boolean? = null
    ): Call<Response<List<GameBestDeal>>>

    @GET("games")
    fun getGame(@Query("id") gameId: GameId): Call<Response<GameInfoResponse>>
}

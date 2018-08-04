package com.fuzzyfunctors.reductions.data.alert

import com.fuzzyfunctors.reductions.domain.game.GameId
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AlertService {

    @GET("alerts")
    fun updateAlert(
            @Query("action") action: String,
            @Query("email") email: String,
            @Query("gameID") gameId: GameId,
            @Query("price") price: Double?
    ): Call<Response<Boolean>>

}

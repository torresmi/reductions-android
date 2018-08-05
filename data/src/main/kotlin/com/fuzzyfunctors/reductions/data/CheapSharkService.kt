package com.fuzzyfunctors.reductions.data

import com.fuzzyfunctors.reductions.data.deal.Deal
import com.fuzzyfunctors.reductions.data.deal.DealInfoResponse
import com.fuzzyfunctors.reductions.data.game.GameBestDeal
import com.fuzzyfunctors.reductions.data.game.GameInfoResponse
import com.fuzzyfunctors.reductions.data.store.Store
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.game.GameId
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CheapSharkService {

    @GET("deals")
    fun getDeals(
            @QueryMap stringParams: Map<String, String>,
            @QueryMap intParams: Map<String, Int>,
            @QueryMap boolParams: Map<String, Boolean>
    ): Call<List<Deal>>

    @GET("deals")
    fun getDeal(@Query("id") dealId: DealId): Call<DealInfoResponse>

    @GET("games")
    fun getGames(
            @Query("title") title: String? = null,
            @Query("steamAppID") steamAppId: Int? = null,
            @Query("limit") limit: Int? = null,
            @Query("exact") exact: Boolean? = null
    ): Call<List<GameBestDeal>>

    @GET("games")
    fun getGame(@Query("id") gameId: GameId): Call<GameInfoResponse>

    @GET("stores")
    fun getStores(): Call<List<Store>>

    @GET("alerts")
    fun updateAlert(
            @Query("action") action: String,
            @Query("email") email: String,
            @Query("gameID") gameId: GameId,
            @Query("price") price: Double?
    ): Call<Boolean>

    companion object {
        const val BASE_URL = "http://www.cheapshark.com/api/1.0/"
    }
}

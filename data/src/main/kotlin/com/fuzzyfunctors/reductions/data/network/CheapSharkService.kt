package com.fuzzyfunctors.reductions.data.network

import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.deal.Deal
import com.fuzzyfunctors.reductions.data.deal.DealInfoResponse
import com.fuzzyfunctors.reductions.data.game.GameBestDeal
import com.fuzzyfunctors.reductions.data.game.GameInfoResponse
import com.fuzzyfunctors.reductions.data.store.Store
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CheapSharkService {
    @GET("deals")
    suspend fun getDeals(
        @QueryMap stringParams: Map<String, String>,
        @QueryMap intParams: Map<String, Int>,
        @QueryMap boolParams: Map<String, Boolean>,
    ): Response<List<Deal>>

    @GET("deals")
    suspend fun getDeal(
        @Query("id") dealId: DealId,
    ): Response<DealInfoResponse>

    @GET("games")
    suspend fun searchGames(
        @Query("title") title: String? = null,
        @Query("steamAppId") steamAppId: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("exact") exact: Boolean? = null,
    ): Response<List<GameBestDeal>>

    @GET("games")
    suspend fun getGame(
        @Query("id") gameId: GameId,
    ): Response<GameInfoResponse>

    @GET("stores")
    suspend fun getStores(): Response<List<Store>>

    @GET("alerts")
    suspend fun updateAlert(
        @Query("action") action: String,
        @Query("email") email: String,
        @Query("gameId") gameId: GameId,
        @Query("price") price: Double?,
    ): Response<Boolean>

    companion object {
        const val BASE_URL = "http://www.cheapshark.com/api/1.0/"
    }
}

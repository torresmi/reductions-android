package com.fuzzyfunctors.reductions.data.network

import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.deal.Deal
import com.fuzzyfunctors.reductions.data.deal.DealInfoResponse
import com.fuzzyfunctors.reductions.data.game.GameBestDeal
import com.fuzzyfunctors.reductions.data.game.GameInfoResponse
import com.fuzzyfunctors.reductions.data.store.Store
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface CheapSharkService {

    @GET("deals")
    fun getDeals(
        @QueryMap stringParams: Map<String, String>,
        @QueryMap intParams: Map<String, Int>,
        @QueryMap boolParams: Map<String, Boolean>
    ): Single<Response<List<Deal>>>

    @GET("deals")
    fun getDeal(@Query("id") dealId: DealId): Single<Response<DealInfoResponse>>

    @GET("games")
    fun searchGames(
        @Query("title") title: String? = null,
        @Query("steamAppId") steamAppId: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("exact") exact: Boolean? = null
    ): Single<Response<List<GameBestDeal>>>

    @GET("games")
    fun getGame(@Query("id") gameId: GameId): Single<Response<GameInfoResponse>>

    @GET("stores")
    suspend fun getStores(): Response<List<Store>>

    @GET("alerts")
    fun updateAlert(
        @Query("action") action: String,
        @Query("email") email: String,
        @Query("gameId") gameId: GameId,
        @Query("price") price: Double?
    ): Single<Response<Boolean>>

    companion object {
        const val BASE_URL = "http://www.cheapshark.com/api/1.0/"
    }
}

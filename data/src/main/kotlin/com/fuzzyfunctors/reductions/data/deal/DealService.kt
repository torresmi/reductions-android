package com.fuzzyfunctors.reductions.data.deal

import com.fuzzyfunctors.reductions.domain.deal.DealId
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface DealService {

    @GET("deals")
    fun getDeals(
            @QueryMap stringParams: Map<String, String>,
            @QueryMap intParams: Map<String, Int>,
            @QueryMap boolParams: Map<String, Boolean>
    ): Call<Response<List<Deal>>>

    @GET("deals")
    fun getDeal(@Query("id") dealId: DealId): Call<Response<DealInfoResponse>>
}

package com.fuzzyfunctors.reductions.data.store

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface StoreService {

    @GET("stores")
    fun getStores(): Call<Response<List<Store>>>
}

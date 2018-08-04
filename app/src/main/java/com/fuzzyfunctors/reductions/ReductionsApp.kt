package com.fuzzyfunctors.reductions

import android.app.Application
import com.fuzzyfunctors.reductions.data.CheapSharkService
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ReductionsApp : Application() {

    //TODO: Inject with Dagger
    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .baseUrl(CheapSharkService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()

    }
}

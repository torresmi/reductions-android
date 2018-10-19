package com.fuzzyfunctors.reductions

import android.app.Application
import android.util.Log
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.store.StoreDataRepository
import com.fuzzyfunctors.reductions.data.store.StoreNetworkDataSource
import com.fuzzyfunctors.reductions.domain.store.StoreRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ReductionsApp : Application() {

    //TODO: Inject with Dagger
    private lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        retrofit = Retrofit.Builder()
                .baseUrl(CheapSharkService.BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val cheapSharkService = retrofit.create(CheapSharkService::class.java)

        val storeReactiveStore = MemoryReactiveStore<StoreId, Store> { store -> store.id }

        val storeNetworkDataSource = StoreNetworkDataSource(cheapSharkService)

        val storeRepository = StoreDataRepository(storeNetworkDataSource, storeReactiveStore)

        storeRepository.getStores()
                .subscribe { Log.d("Stores", it.toString()) }

        storeRepository.fetchStores()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Log.d("Stores", "Network response error") }, { Log.d("Stores", "IO Error") }, { Log.d("Stores", "Network fetch success")})

        storeRepository.getStore("10")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Log.d("Stores", "Got store $it") }
    }
}

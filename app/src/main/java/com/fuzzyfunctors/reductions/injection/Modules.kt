package com.fuzzyfunctors.reductions.injection

import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.core.store.Store
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.data.MemoryReactiveStore
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.data.alert.AlertDataRepository
import com.fuzzyfunctors.reductions.data.alert.AlertNetworkDataSource
import com.fuzzyfunctors.reductions.data.deal.DealDataRepository
import com.fuzzyfunctors.reductions.data.deal.DealNetworkDataSource
import com.fuzzyfunctors.reductions.data.deal.DealType
import com.fuzzyfunctors.reductions.data.deal.DealTypeData
import com.fuzzyfunctors.reductions.data.game.GameDataRepository
import com.fuzzyfunctors.reductions.data.game.GameNetworkDataSource
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.store.StoreDataRepository
import com.fuzzyfunctors.reductions.data.store.StoreNetworkDataSource
import com.fuzzyfunctors.reductions.domain.alert.AlertRepository
import com.fuzzyfunctors.reductions.domain.deal.DealRepository
import com.fuzzyfunctors.reductions.domain.game.GameRepository
import com.fuzzyfunctors.reductions.domain.store.StoreRepository
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

val appModule = module {
}

val repositoryModule = module {

    single<StoreRepository> { StoreDataRepository(get(), get(named(MEMORY_STORE_STORE))) }

    single<GameRepository> { GameDataRepository(get(), get(named(MEMORY_STORE_GAME))) }

    single<DealRepository> {
        DealDataRepository(
                get(),
                get(named(MEMORY_STORE_DEALS)),
                get(named(MEMORY_STORE_DEAL)))
    }

    single<AlertRepository> { AlertDataRepository(get()) }
}

val localModule = module {

    factory<ReactiveStore<StoreId, Store>>(named(MEMORY_STORE_STORE)) {
        MemoryReactiveStore { store -> store.id }
    }

    factory<ReactiveStore<GameId, Game>>(named(MEMORY_STORE_GAME)) {
        MemoryReactiveStore { game -> game.id }
    }

    factory<ReactiveStore<DealType, DealTypeData>>(named(MEMORY_STORE_DEALS)) {
        MemoryReactiveStore { (type, _) -> type }
    }

    factory<ReactiveStore<DealId, DealInfo>>(named(MEMORY_STORE_DEAL)) {
        MemoryReactiveStore { deal -> deal.id }
    }
}

val networkModule = module {

    single<CheapSharkService> {
        val retrofit = buildRetrofit()
        retrofit.create(CheapSharkService::class.java)
    }

    factory { StoreNetworkDataSource(get()) }

    factory { GameNetworkDataSource(get()) }

    factory { DealNetworkDataSource(get()) }

    factory { AlertNetworkDataSource(get()) }
}

private fun buildRetrofit(): Retrofit {
    return Retrofit.Builder()
            .baseUrl(CheapSharkService.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}

private const val MEMORY_STORE_STORE = "storeMemoryStore"
private const val MEMORY_STORE_GAME = "storeMemoryGame"
private const val MEMORY_STORE_DEALS = "storeMemoryDeals"
private const val MEMORY_STORE_DEAL = "storeMemoryDeal"

object Modules {

    fun get(isDebug: Boolean): List<Module> {
        return listOf(
                appModule,
                networkModule,
                localModule,
                repositoryModule
        )
    }
}

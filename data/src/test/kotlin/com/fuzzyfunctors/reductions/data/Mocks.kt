package com.fuzzyfunctors.reductions.data

import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.store.StoreNetworkDataSource
import io.mockk.mockk

object Mocks {
    val mockCheapSharkService = mockk<CheapSharkService>()
    val mockStoreNetworkDataSource = mockk<StoreNetworkDataSource>()
}

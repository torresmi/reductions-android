package com.fuzzyfunctors.reductions.data

import com.fuzzyfunctors.reductions.data.alert.AlertNetworkDataSource
import com.fuzzyfunctors.reductions.data.deal.DealNetworkDataSource
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.store.StoreNetworkDataSource
import io.mockk.mockk

object Mocks {
    val mockCheapSharkService = mockk<CheapSharkService>()
    val mockStoreNetworkDataSource = mockk<StoreNetworkDataSource>()
    val mockDealNetworkDataSource = mockk<DealNetworkDataSource>()
    val mockAlertNetworkDataSource = mockk<AlertNetworkDataSource>()
}

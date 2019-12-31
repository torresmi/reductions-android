package com.fuzzyfunctors.reductions.data

import com.fuzzyfunctors.reductions.data.alert.AlertNetworkDataSource
import com.fuzzyfunctors.reductions.data.deal.DealNetworkDataSource
import com.fuzzyfunctors.reductions.data.game.GameNetworkDataSource
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import io.mockk.mockk

object Mocks {
    val mockCheapSharkService = mockk<CheapSharkService>()
    val mockDealNetworkDataSource = mockk<DealNetworkDataSource>()
    val mockAlertNetworkDataSource = mockk<AlertNetworkDataSource>()
    val mockGameNetworkDataSource = mockk<GameNetworkDataSource>()
}

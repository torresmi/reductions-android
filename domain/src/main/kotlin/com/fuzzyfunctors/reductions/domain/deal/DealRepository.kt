package com.fuzzyfunctors.reductions.domain.deal

import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import kotlinx.coroutines.flow.Flow

interface DealRepository {

    fun getTopDeals(): Flow<List<Deal>?>

    suspend fun fetchTopDeals(options: Options = Options()): LoadingFailure.Remote?

    fun getNewestGamesDeals(): Flow<List<Deal>?>

    suspend fun fetchNewestGamesDeals(options: Options = Options()): LoadingFailure.Remote?

    fun getLatestDeals(): Flow<List<Deal>?>

    suspend fun fetchLatestDeals(options: Options = Options()): LoadingFailure.Remote?

    fun getMostSavingsDeals(): Flow<List<Deal>?>

    suspend fun fetchMostSavingsDeals(options: Options = Options()): LoadingFailure.Remote?

    fun getDealInfo(id: DealId): Flow<DealInfo?>

    suspend fun fetchDealInfo(id: DealId): LoadingFailure.Remote?

    data class Options(
        val continuePagination: Boolean = true,
        val limit: Int? = null,
        val order: Order? = null,
        val filters: Filters? = null
    ) {

        enum class Order {
            DEAL_RATING,
            TITLE,
            SAVINGS,
            PRICE,
            METACRITIC,
            REVIEWS,
            RELEASE,
            STORE,
            RECENT
        }

        data class Filters(
            val storeId: Set<StoreId>? = null,
            val minimumPrice: Int? = null,
            val maximumPrice: Int? = null,
            val minimumMetacriticRating: Int? = null,
            val minimumSteamRating: Int? = null,
            val tripleA: Boolean? = null,
            val steamOnly: Boolean? = null,
            val onSale: Boolean? = null
        )

        companion object {
            const val NO_LIMIT = 50
            const val MINIMUM_PRICE = 0
        }
    }
}

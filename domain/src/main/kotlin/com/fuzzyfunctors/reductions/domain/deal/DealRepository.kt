package com.fuzzyfunctors.reductions.domain.deal

import arrow.core.Option
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.core.store.StoreId
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import io.reactivex.Maybe
import io.reactivex.Observable

interface DealRepository {

    fun getTopDeals(): Observable<Option<List<Deal>>>

    fun fetchTopDeals(options: Options = Options()): Maybe<LoadingFailure.Remote>

    fun getNewestGamesDeals(): Observable<Option<List<Deal>>>

    fun fetchNewestGamesDeals(options: Options = Options()): Maybe<LoadingFailure.Remote>

    fun getLatestDeals(): Observable<Option<List<Deal>>>

    fun fetchLatestDeals(options: Options = Options()): Maybe<LoadingFailure.Remote>

    fun getMostSavingsDeals(): Observable<Option<List<Deal>>>

    fun fetchMostSavingsDeals(options: Options = Options()): Maybe<LoadingFailure.Remote>

    fun getDealInfo(id: DealId): Observable<Option<DealInfo>>

    fun fetchDealInfo(id: DealId): Maybe<LoadingFailure.Remote>

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

package com.fuzzyfunctors.reductions.data.deal

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.deal.DealRepository
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class DealDataRepository(
    private val networkDataSource: DealNetworkDataSource,
    private val dealsMemoryReactiveStore: ReactiveStore<DealType, DealTypeData>,
    private val dealInfoMemoryReactiveStore: ReactiveStore<DealId, DealInfo>
) : DealRepository {

    // Keep requests ordered for pagination
    private val topDealsDispatcher = createNewSingleThreadDispatcher()
    private val newestGameDealsDispatcher = createNewSingleThreadDispatcher()
    private val latestGameDealsDispatcher = createNewSingleThreadDispatcher()
    private val mostSavingsDealsDispatcher = createNewSingleThreadDispatcher()

    private val paginator = Paginator()

    override fun getTopDeals(): Flow<List<Deal>?> = getDealsForType(DealType.TOP)

    override suspend fun fetchTopDeals(options: DealRepository.Options): LoadingFailure.Remote? =
        withContext(topDealsDispatcher) { fetchDealsForType(DealType.TOP, options) }

    override fun getNewestGamesDeals(): Flow<List<Deal>?> = getDealsForType(DealType.NEWEST_GAMES)

    override suspend fun fetchNewestGamesDeals(
        options: DealRepository.Options,
    ): LoadingFailure.Remote? =
        withContext(newestGameDealsDispatcher) { fetchDealsForType(DealType.NEWEST_GAMES, options) }

    override fun getLatestDeals(): Flow<List<Deal>?> = getDealsForType(DealType.LATEST)

    override suspend fun fetchLatestDeals(options: DealRepository.Options): LoadingFailure.Remote? =
        withContext(latestGameDealsDispatcher) { fetchDealsForType(DealType.LATEST, options) }

    override fun getMostSavingsDeals(): Flow<List<Deal>?> = getDealsForType(DealType.MOST_SAVINGS)

    override suspend fun fetchMostSavingsDeals(
        options: DealRepository.Options,
    ): LoadingFailure.Remote? =
        withContext(mostSavingsDealsDispatcher) {
            fetchDealsForType(DealType.MOST_SAVINGS, options)
        }

    override fun getDealInfo(id: DealId): Flow<DealInfo?> = dealInfoMemoryReactiveStore.get(id)

    override suspend fun fetchDealInfo(id: DealId): LoadingFailure.Remote? =
        when (val response = networkDataSource.getDeal(id)) {
            is Either.Left -> {
                response.a
            }
            is Either.Right -> {
                dealInfoMemoryReactiveStore.store(response.b)
                null
            }
        }

    private fun getDealsForType(type: DealType): Flow<List<Deal>?> =
        dealsMemoryReactiveStore.get(type)
            .map { it?.deals }

    private suspend fun fetchDealsForType(
        type: DealType,
        options: DealRepository.Options
    ): LoadingFailure.Remote? {
        val optionsWithOrder = options.copy(order = type.toOrder())
        val page = paginator.getPageforOptions(type, optionsWithOrder)
        return when (val response = networkDataSource.getDeals(optionsWithOrder, page)) {
            is Either.Left -> {
                response.a
            }
            is Either.Right -> {
                paginator.onSuccessfulResponse(type, optionsWithOrder)
                dealsMemoryReactiveStore.store(DealTypeData(type, response.b))
                null
            }
        }
    }

    private fun createNewSingleThreadDispatcher() =
        Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private fun DealType.toOrder(): DealRepository.Options.Order = when (this) {
        DealType.MOST_SAVINGS -> DealRepository.Options.Order.SAVINGS
        DealType.LATEST -> DealRepository.Options.Order.RECENT
        DealType.NEWEST_GAMES -> DealRepository.Options.Order.RELEASE
        DealType.TOP -> DealRepository.Options.Order.DEAL_RATING
    }
}

private class Paginator {
    private val pages = HashMap<DealType, Int?>()

    fun getPageforOptions(dealType: DealType, options: DealRepository.Options): Int? {
        val currentPage = pages[dealType]
        return if (options.continuePagination) {
            currentPage?.plus(1)
        } else {
            null
        }
    }

    fun onSuccessfulResponse(dealType: DealType, options: DealRepository.Options) {
        val nextPage = if (options.continuePagination) {
            val currentPage = pages[dealType]
            currentPage?.plus(1) ?: 1
        } else {
            null
        }
        pages[dealType] = nextPage
    }
}

enum class DealType {
    TOP,
    NEWEST_GAMES,
    LATEST,
    MOST_SAVINGS
}

data class DealTypeData(val type: DealType, val deals: List<Deal>)

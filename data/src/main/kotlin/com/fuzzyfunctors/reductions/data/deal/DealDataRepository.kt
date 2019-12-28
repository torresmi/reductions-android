package com.fuzzyfunctors.reductions.data.deal

import arrow.core.Option
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.ReactiveStore
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.deal.DealRepository
import com.fuzzyfunctors.reductions.domain.toMaybeLeft
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors

class DealDataRepository(
    private val networkDataSource: DealNetworkDataSource,
    private val dealsMemoryReactiveStore: ReactiveStore<DealType, DealTypeData>,
    private val dealInfoMemoryReactiveStore: ReactiveStore<DealId, DealInfo>
) : DealRepository {

    // Keep requests ordered for pagination
    private val topDealsScheduler = createNewSingleThreadScheduler()
    private val newestGameDealsScheduler = createNewSingleThreadScheduler()
    private val latestGameDealsScheduler = createNewSingleThreadScheduler()
    private val mostSavingsDealsScheduler = createNewSingleThreadScheduler()

    private val paginator = Paginator()

    override fun getTopDeals(): Observable<Option<List<Deal>>> = getDealsForType(DealType.TOP)

    override fun fetchTopDeals(options: DealRepository.Options): Maybe<LoadingFailure.Remote> =
            fetchDealsForType(DealType.TOP, options)
                    .subscribeOn(topDealsScheduler)

    override fun getNewestGamesDeals(): Observable<Option<List<Deal>>> =
            getDealsForType(DealType.NEWEST_GAMES)

    override fun fetchNewestGamesDeals(options: DealRepository.Options): Maybe<LoadingFailure.Remote> =
            fetchDealsForType(DealType.NEWEST_GAMES, options)
                    .subscribeOn(newestGameDealsScheduler)

    override fun getLatestDeals(): Observable<Option<List<Deal>>> = getDealsForType(DealType.LATEST)

    override fun fetchLatestDeals(options: DealRepository.Options): Maybe<LoadingFailure.Remote> =
            fetchDealsForType(DealType.LATEST, options)
                    .subscribeOn(latestGameDealsScheduler)

    override fun getMostSavingsDeals(): Observable<Option<List<Deal>>> =
            getDealsForType(DealType.MOST_SAVINGS)

    override fun fetchMostSavingsDeals(options: DealRepository.Options): Maybe<LoadingFailure.Remote> =
            fetchDealsForType(DealType.MOST_SAVINGS, options)
                    .subscribeOn(mostSavingsDealsScheduler)

    override fun getDealInfo(id: DealId): Observable<Option<DealInfo>> =
            dealInfoMemoryReactiveStore.get(id)

    override fun fetchDealInfo(id: DealId): Maybe<LoadingFailure.Remote> =
            networkDataSource.getDeal(id)
                    .doAfterSuccess { response ->
                        response.fold(
                                {},
                                { dealInfoMemoryReactiveStore.store(it) }
                        )
                    }
                    .flatMapMaybe { it.toMaybeLeft() }

    private fun getDealsForType(type: DealType): Observable<Option<List<Deal>>> =
            dealsMemoryReactiveStore.get(type)
                    .map(getDeals)

    private val getDeals = { data: Option<DealTypeData> ->
        data.map { it.deals }
    }

    private fun fetchDealsForType(
        type: DealType,
        options: DealRepository.Options
    ): Maybe<LoadingFailure.Remote> {
        val optionsWithOrder = options.copy(order = type.toOrder())
        val page = paginator.getPageforOptions(type, optionsWithOrder)
        return networkDataSource.getDeals(optionsWithOrder, page)
                .map { response -> response.map { DealTypeData(type, it) } }
                .doOnSuccess { response ->
                    response.fold(
                            {},
                            {
                                paginator.onSuccessfulResponse(type, optionsWithOrder)
                                dealsMemoryReactiveStore.store(it)
                            }
                    )
                }
                .flatMapMaybe { it.toMaybeLeft() }
    }

    private fun createNewSingleThreadScheduler() =
            Schedulers.from(Executors.newSingleThreadExecutor())

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

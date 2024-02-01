package com.fuzzyfunctors.reductions.data.deal

import arrow.core.Either
import com.fuzzyfunctors.reductions.core.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.data.network.CheapSharkService
import com.fuzzyfunctors.reductions.data.network.toCore
import com.fuzzyfunctors.reductions.data.network.toEither
import com.fuzzyfunctors.reductions.domain.LoadingFailure
import com.fuzzyfunctors.reductions.domain.deal.DealRepository.Options

class DealNetworkDataSource(private val networkService: CheapSharkService) {
    suspend fun getDeals(
        options: Options,
        pageNumber: Int? = null,
    ): Either<LoadingFailure.Remote, List<Deal>> {
        val params = toParams(options, pageNumber)
        return networkService.getDeals(
            params.stringParams.orEmpty(),
            params.intParams.orEmpty(),
            params.boolParams.orEmpty(),
        )
            .toEither()
            .map { deals ->
                deals.map { it.toCore() }
            }
    }

    suspend fun getDeal(dealId: DealId): Either<LoadingFailure.Remote, DealInfo> =
        networkService.getDeal(
            dealId,
        )
            .toEither()
            .map { it.toCore(dealId) }

    private data class Params(
        val stringParams: Map<String, String>?,
        val boolParams: Map<String, Boolean>?,
        val intParams: Map<String, Int>?,
    )

    private fun toParams(
        options: Options,
        pageNumber: Int? = null,
    ): Params {
        val filters = options.filters
        val stringParams = mutableMapOf<String, String>()
        val boolParams = mutableMapOf<String, Boolean>()
        val intParams = mutableMapOf<String, Int>()

        filters?.run {
            storeId.orEmpty().forEach { stringParams[Param.STORE_ID.paramName] = it }
            minimumPrice?.also { intParams[Param.MIN_PRICE.paramName] = it }
            maximumPrice?.also { intParams[Param.MAX_PRICE.paramName] = it }
            minimumMetacriticRating?.also { intParams[Param.MIN_METACRITIC_RATING.paramName] = it }
            minimumSteamRating?.also { intParams[Param.MIN_STEAM_RATING.paramName] = it }
            tripleA?.also { boolParams[Param.TRIPLE_A.paramName] = it }
            steamOnly?.also { boolParams[Param.STEAM_ONLY.paramName] = it }
            onSale?.also { boolParams[Param.ON_SALE.paramName] = it }
        }

        options.order?.also { stringParams[Param.ORDER.paramName] = orderParamName(it) }
        pageNumber?.also { intParams[Param.PAGE_NUMBER.paramName] = it }

        return Params(
            stringParams,
            boolParams,
            intParams,
        )
    }

    private fun orderParamName(order: Options.Order): String = when (order) {
        Options.Order.DEAL_RATING -> OrderParam.DEAL_RATING.paramName
        Options.Order.TITLE -> OrderParam.TITLE.paramName
        Options.Order.SAVINGS -> OrderParam.SAVINGS.paramName
        Options.Order.PRICE -> OrderParam.PRICE.paramName
        Options.Order.METACRITIC -> OrderParam.METACRITIC.paramName
        Options.Order.REVIEWS -> OrderParam.REVIEWS.paramName
        Options.Order.RELEASE -> OrderParam.RELEASE.paramName
        Options.Order.STORE -> OrderParam.STORE.paramName
        Options.Order.RECENT -> OrderParam.RECENT.paramName
    }

    private enum class Param(val paramName: String) {
        STORE_ID("storeID"),
        MIN_PRICE("lowerPrice"),
        MAX_PRICE("upperPrice"),
        MIN_METACRITIC_RATING("metacritic"),
        MIN_STEAM_RATING("steamRating"),
        TRIPLE_A("AAA"),
        STEAM_ONLY("steamworks"),
        ON_SALE("onSale"),
        ORDER("sortBy"),
        LIMIT("pageSize"),
        PAGE_NUMBER("pageNumber"),
    }

    enum class OrderParam(val paramName: String) {
        DEAL_RATING("Deal Rating"),
        TITLE("Title"),
        SAVINGS("Savings"),
        PRICE("Price"),
        METACRITIC("Metacritic"),
        REVIEWS("Reviews"),
        RELEASE("Release"),
        STORE("Store"),
        RECENT("recent"),
    }
}

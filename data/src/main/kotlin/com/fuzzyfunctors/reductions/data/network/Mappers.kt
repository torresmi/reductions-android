package com.fuzzyfunctors.reductions.data.network

import com.fuzzyfunctors.reductions.core.deal.DealId
import com.fuzzyfunctors.reductions.core.deal.DealInfo
import com.fuzzyfunctors.reductions.core.game.CheapestPriceEver
import com.fuzzyfunctors.reductions.core.game.Game
import com.fuzzyfunctors.reductions.core.game.GameId
import com.fuzzyfunctors.reductions.data.deal.Deal
import com.fuzzyfunctors.reductions.data.deal.DealInfoResponse
import com.fuzzyfunctors.reductions.data.game.GameBestDeal
import com.fuzzyfunctors.reductions.data.game.GameInfoResponse
import com.fuzzyfunctors.reductions.data.store.Store
import java.util.*
import com.fuzzyfunctors.reductions.core.deal.Deal as CoreDeal
import com.fuzzyfunctors.reductions.core.game.GameBestDeal as CoreGameBestDeal
import com.fuzzyfunctors.reductions.core.store.Store as CoreStore

fun Store.toCore(): CoreStore {
    val images = images
    return CoreStore(
        id = storeID,
        name = storeName,
        isActive = isActive == 1,
        images = CoreStore.Images(
            banner = images.banner,
            logo = images.logo,
            icon = images.icon,
        ),
    )
}

fun Deal.toCore(): CoreDeal = CoreDeal(
    id = dealID,
    storeId = storeID,
    gameId = gameID,
    title = title,
    saleData = saleData(
        isOnSale = isOnSale == "1",
        salePrice = salePrice,
        savings = savings,
        dealRating = dealRating,
    ),
    normalPrice = normalPrice,
    metacriticData = metacriticData(
        metacriticLink = metacriticLink,
        metacriticScore = metacriticScore,
    ),
    steamData = steamData(
        steamAppID = steamAppID,
        steamRatingCount = steamRatingCount,
        steamRatingText = steamRatingText,
        steamRatingPercent = steamRatingPercent,
    ),
    releaseDate = Date(releaseDate),
    lastChange = lastChange?.let { Date(it) },
    iconLink = thumb,
)

fun DealInfoResponse.toCore(dealId: DealId): DealInfo {
    val isOnSale = gameInfo.salePrice != gameInfo.retailPrice
    val salePrice = if (isOnSale) gameInfo.salePrice else null

    return DealInfo(
        id = dealId,
        storeId = gameInfo.storeID,
        gameId = gameInfo.gameID,
        title = gameInfo.name,
        salePrice = salePrice,
        normalPrice = gameInfo.retailPrice,
        metacriticData = metacriticData(
            metacriticLink = gameInfo.metacriticLink,
            metacriticScore = gameInfo.metacriticScore,
        ),
        steamData = steamData(
            steamAppID = gameInfo.steamAppID,
            steamRatingCount = gameInfo.steamRatingCount,
            steamRatingText = gameInfo.steamRatingText,
            steamRatingPercent = gameInfo.steamRatingPercent,
        ),
        releaseDate = Date(gameInfo.releaseDate),
        iconLink = gameInfo.thumb,
        publisher = gameInfo.publisher,
        steamWorks = gameInfo.steamworks == "1",
        cheapestPriceEver = cheapestPrice.toCore(),
        cheaperStores = cheaperStores.map { it.toCore() }.associateBy { it.storeId },
    )
}

fun DealInfoResponse.CheaperStore.toCore(): DealInfo.CheaperStore = DealInfo.CheaperStore(
    storeId = storeID,
    dealId = dealID,
    salePrice = salePrice,
    normalPrice = retailPrice,
)

fun DealInfoResponse.CheapestPrice.toCore(): CheapestPriceEver = CheapestPriceEver(
    price = price,
    date = Date(date),
)

fun GameInfoResponse.toCoreGame(gameId: GameId): Game = Game(
    id = gameId,
    steamAppId = info.steamAppID,
    title = info.title,
    cheapestPriceEver = cheapestPriceEver.toCore(),
    deals = deals.map { it.toCore() }.associateBy { it.dealId },
)

fun GameInfoResponse.CheapestPriceEver.toCore(): CheapestPriceEver = CheapestPriceEver(
    price = price,
    date = Date(date),
)

fun GameInfoResponse.Deal.toCore(): Game.DealInfo = Game.DealInfo(
    dealId = dealID,
    storeId = storeID,
    price = price,
    retailPrice = retailPrice,
    savings = savings,
)

fun GameBestDeal.toCore(): CoreGameBestDeal = CoreGameBestDeal(
    gameId = gameID,
    steamAppId = steamAppID,
    cheapest = cheapest,
    cheapestDealId = cheapestDealID,
    title = external,
    thumb = thumb,
)

@Suppress("ComplexCondition")
private fun steamData(
    steamAppID: String?,
    steamRatingCount: String?,
    steamRatingText: String?,
    steamRatingPercent: String?,
): CoreDeal.SteamData? = if (steamAppID != null &&
    steamRatingCount != null &&
    steamRatingText != null &&
    steamRatingPercent != null
) {
    CoreDeal.SteamData(
        appId = steamAppID,
        ratingText = steamRatingText,
        ratingCount = steamRatingCount,
        ratingPercent = steamRatingPercent,
    )
} else {
    null
}

private fun metacriticData(
    metacriticLink: String?,
    metacriticScore: String?,
): CoreDeal.MetacriticData? = metacriticLink?.let { link ->
    metacriticScore?.let { score ->
        CoreDeal.MetacriticData(
            link = link,
            score = score,
        )
    }
}

private fun saleData(
    isOnSale: Boolean,
    salePrice: String,
    savings: String,
    dealRating: String,
): CoreDeal.SaleData? = if (!isOnSale) {
    null
} else {
    CoreDeal.SaleData(
        price = salePrice,
        savings = savings,
        rating = dealRating,
    )
}

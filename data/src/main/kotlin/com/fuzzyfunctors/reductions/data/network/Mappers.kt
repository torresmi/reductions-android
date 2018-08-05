package com.fuzzyfunctors.reductions.data.network

import com.fuzzyfunctors.reductions.data.deal.Deal
import com.fuzzyfunctors.reductions.core.deal.Deal as CoreDeal
import com.fuzzyfunctors.reductions.data.store.Store
import java.util.Date
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
                    icon = images.icon
            )
    )
}

fun Deal.toCore(): CoreDeal {
    return CoreDeal(
            id = dealID,
            storeId = storeID,
            gameId = gameID,
            title = title,
            saleData = saleData(),
            normalPrice = normalPrice,
            metacriticData = metacriticData(),
            steamData = steamData(),
            releaseDate = Date(releaseDate),
            lastChange = lastChange?.let { Date(it) },
            iconLink = thumb,
            gameInfo = null,
            cheaperStores = null
    )
}

private fun Deal.steamData(): CoreDeal.SteamData? =
        steamAppID?.let { id ->
            steamRatingCount?.let { count ->
                steamRatingText?.let { text ->
                    steamRatingPercent?.let { percent ->
                        CoreDeal.SteamData(
                                appId = id,
                                ratingText = text,
                                ratingCount = count,
                                ratingPercent = percent
                        )
                    }
                }
            }
        }

private fun Deal.metacriticData(): CoreDeal.MetacriticData? =
        metacriticLink?.let { link ->
            metacriticScore?.let { score ->
                CoreDeal.MetacriticData(
                        link = link,
                        score = score
                )
            }
        }

private fun Deal.saleData(): CoreDeal.SaleData? =
        if (isOnSale == "0") {
            null
        } else {
            CoreDeal.SaleData(
                    price = salePrice,
                    savings = savings,
                    rating = dealRating
            )
        }

package com.fuzzyfunctors.reductions.deals

import androidx.annotation.StringRes
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fuzzyfunctors.reductions.R

// TODO: Move to view model
private enum class DealType(@StringRes val titleRes: Int) {
    TOP(R.string.deals_top_title),
    NEWEST_GAMES(R.string.deals_newest_games_title),
    LATEST_DEALS(R.string.deals_latest_deals_title),
    MOST_SAVINGS(R.string.deals_most_savings_title)
}

@Composable
fun DealTabs(modifier: Modifier = Modifier, selectedIndex: Int = 0) {
    TabRow(selectedTabIndex = selectedIndex, modifier = modifier) {
        DealType.values().forEachIndexed { index, type ->
            Tab(
                text = { Text(stringResource(type.titleRes)) },
                selected = index == selectedIndex,
                onClick = { /*TODO*/ },
            )
        }
    }
}

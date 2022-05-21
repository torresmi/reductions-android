package com.fuzzyfunctors.reductions

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.fuzzyfunctors.reductions.deals.DealTabs
import com.fuzzyfunctors.reductions.ui.theme.ReductionsTheme

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReductionsTheme {
                Scaffolding()
            }
        }
    }
}

@Composable
fun Scaffolding() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(id = R.string.deals_title)) },
            )
        },
    ) {
        Column(Modifier.padding(it)) {
            DealTabs()
        }
    }
}

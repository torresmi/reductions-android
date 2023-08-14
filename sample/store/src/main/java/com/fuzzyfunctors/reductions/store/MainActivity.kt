package com.fuzzyfunctors.reductions.store

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fuzzyfunctors.reductions.store.ui.theme.ReductionsandroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReductionsandroidTheme {
            }
        }
    }
}

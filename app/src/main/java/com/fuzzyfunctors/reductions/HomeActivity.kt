package com.fuzzyfunctors.reductions

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.fuzzyfunctors.reductions.ui.theme.ReductionsTheme

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReductionsTheme {
            }
        }
    }
}

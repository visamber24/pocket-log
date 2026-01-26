package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.lazysloth.pocketlog.ui.ApplicationBottomNavigation

@Composable
fun StatsScreen(){
    Scaffold(
        topBar = {
            Text(
                text = ApplicationBottomNavigation.STATS.name,
            )
        }
    ) {

    }
}
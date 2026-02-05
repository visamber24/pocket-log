package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lazysloth.pocketlog.ui.navigationitem.ApplicationBottomNavigation
import com.lazysloth.pocketlog.ui.screen.contentscreen.StatsScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = ApplicationBottomNavigation.STATS.name,
//                        modifier = Modifier.padding(20.dp)
                    )
                },
                )
        }
    ) {
        StatsScreenContent(Modifier.padding(it))
    }
}


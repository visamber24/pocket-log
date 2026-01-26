package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.ui.ApplicationBottomNavigation

@Composable
fun DashboardScreen(){
    Scaffold(
        topBar = {
            Text(
                text = ApplicationBottomNavigation.HOME.name,
            )
        }
    ) {

    }
}
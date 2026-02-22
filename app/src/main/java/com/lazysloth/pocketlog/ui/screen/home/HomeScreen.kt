package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.lazysloth.pocketlog.ui.navigationitem.ApplicationBottomNavigation

@Composable
fun HomeScreen(
    onClickAdd : () -> Unit,
    onClickAi: () -> Unit,
    modifier: Modifier = Modifier) {
    var currentScreen by rememberSaveable { mutableStateOf(ApplicationBottomNavigation.HOME) }

    NavigationSuiteScaffold(
         navigationSuiteItems = {
             ApplicationBottomNavigation.entries.forEach {
                 item(
                     icon =
                         {
                             Icon(
                                 painter = painterResource(id = it.icon ),
                                 contentDescription = it.label
                             )
                         },
                     selected = it == currentScreen,
                     onClick = {currentScreen = it}
                 )

             }
         }
    )
    {
        Scaffold(Modifier.fillMaxSize()) {innerPadding ->
            Column(
                modifier = Modifier.padding(
                    bottom = innerPadding.calculateBottomPadding()
                )
            ) {
                when(currentScreen)
                {

                    ApplicationBottomNavigation.HOME -> {
                        DashboardScreen(onClickAdd = { onClickAdd() })
                    }
                    ApplicationBottomNavigation.ACCOUNT ->  {
                        AccountScreen()
                    }

                    ApplicationBottomNavigation.STATS -> {
                        StatsScreen()
                    }

                    ApplicationBottomNavigation.PROFILE -> {
                        ProfileScreen()
                    }
                }
            }

        }
    }

}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview()
{
    HomeScreen(onClickAi = {}, onClickAdd = {})
}
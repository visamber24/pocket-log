package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.di.AppViewModelProvider
import com.lazysloth.pocketlog.ui.navigationitem.ApplicationBottomNavigation
import com.lazysloth.pocketlog.ui.navigationitem.NavigationDestination
import com.lazysloth.pocketlog.ui.screen.contentscreen.DashboardScreenContent
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.DashboardScreenViewModel
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme

object DashboardScreenDestination : NavigationDestination {
    override val route = "item_details"
    override val titleRes = R.string.dashboard_screen
    const val itemIdArg = "dashboardId"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onClickAdd: () -> Unit,
    viewmodel: DashboardScreenViewModel = viewModel(
        factory = AppViewModelProvider.Factory
    )
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = ApplicationBottomNavigation.HOME.name,
//                        modifier = Modifier.padding(20.dp)
                )
            }, actions = {
                IconButton(content = {
                    Icon(
                        painter = painterResource(R.drawable.settings_24px),
                        contentDescription = stringResource(R.string.settings),
                    )
                }, onClick = {

                }

                )
            })
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,

                ) {
                FloatingActionButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.visibility_24px),
                        contentDescription = "Capture with the camera"
                    )
                }
                FloatingActionButton(onClick = {
                    onClickAdd()
                }) {
                    Icon(
                        painter = painterResource(R.drawable.add_24px),
                        contentDescription = "Add new record"
                    )
                }
            }
        }
    ) {innerPadding ->
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val uiState by viewmodel.uiState.collectAsState()
        DashboardScreenContent(uiState.transList,Modifier.padding(innerPadding))
    }
}


@Preview(showSystemUi = true)
@Composable
fun DashboardPreview() {
    PocketLogTheme { DashboardScreen(onClickAdd = {}) }
}
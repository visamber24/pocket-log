package com.lazysloth.pocketlog.ui.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.ui.screen.contentscreen.CategoryContentScreen
import com.lazysloth.pocketlog.ui.screen.home.viewmodel.CategoryScreenViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    onClickAdd: () -> Unit,

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Category") }
            )
        },
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.End,
            ) {
                FloatingActionButton(onClick = {onClickAdd()}) {
                    Icon(
                        painter = painterResource(R.drawable.add_24px),
                        contentDescription = "add Category"
                    )
                }
            }
        }
    ) {innerPadding ->
        val vm: CategoryScreenViewModel = koinViewModel()
        val uiState by vm.uiState.collectAsState()
        CategoryContentScreen(modifier = Modifier.padding(innerPadding),uiState.categoryList,viewModel = vm)
    }
}
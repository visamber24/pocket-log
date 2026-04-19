package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.lazysloth.pocketlog.R
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.AddAccountViewModel
import com.lazysloth.pocketlog.ui.screen.home.uiState.AddAccountUiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddAccountScreen() {
    val vm: AddAccountViewModel = koinViewModel()
    val uiState by vm.uiState.collectAsState()
    AddAccountScreenImpl(uiState= uiState,onSave = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAccountScreenImpl(uiState: AddAccountUiState,onSave: ()->Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Account") }
            )
        }
    ){innerPadding->
        Column(
//            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ){

        }

        Spacer(Modifier.height(8.dp))
        Button(
            onClick = {
                onSave()


            },
            enabled = (uiState.addAmount.isNotBlank() && uiState.options.isNotEmpty())
        ) {
            Text("Save")
        }
    }
}
package com.lazysloth.pocketlog.ui.screen.contentscreen

import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.EditAccountScreenViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EditAccountScreen(popBackStack: () -> Unit) {

    val vm: EditAccountScreenViewModel = koinViewModel(
        viewModelStoreOwner = LocalActivity.current as ComponentActivity
    )
    val uiState by vm.uiState.collectAsState()
    AddAccountScreenImpl(
        uiState = uiState,
        onSave = {
            vm.onUpdate()
            popBackStack()
        },
        onInitialBalanceChange = vm::onInitialBalanceChange,
        onAccountSelected = vm::onAccountSelected,
        onAccountNameChange = vm::onAccountNameChange,
        onExpandedAccountType = vm::onExpandedAccountType
    )
}
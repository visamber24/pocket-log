package com.lazysloth.pocketlog.ui.screen.contentscreen

import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.lazysloth.pocketlog.ui.screen.contentscreen.viewmodel.EditTransactionScreenViewmodel
import com.lazysloth.pocketlog.ui.theme.PocketLogTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TransactionEditScreen(onSavePopBackStack: () -> Unit) {

    val vm: EditTransactionScreenViewmodel = koinViewModel(
        viewModelStoreOwner = LocalActivity.current as ComponentActivity
    )
    val transactionItem by vm.uiState.collectAsState()
    val context = LocalContext.current.applicationContext
    AddTransactionScreenImpl(
        uiState = transactionItem,
        onAccountSelected = vm::onAccountSelected,
        onAmountChange = vm::onAmountChange,
        onExpandedAccount = vm::onExpandedAccount,
        onExpandedCategory = vm::onExpandedChange,
        onOptionSelected = vm::onOptionSelected,
        onTransactionTypeSelected = vm::onTransactionTypeSelected,
        onNoteValueChange = vm::onNoteValueChange,
        onDescriptionChange = vm::onDescriptionChange,
        onClickDate = vm::onClickDate,
        onDateChange = vm::onDateChange,
        onSave = {
            vm.updateTransaction()
            onSavePopBackStack()
            Toast.makeText(context,"Transaction Updated!!", Toast.LENGTH_SHORT).show()
        },
    )

}

@Composable
@Preview(showSystemUi = true)
fun TransactionEditScreenPreview() {
    PocketLogTheme {
        TransactionEditScreen(onSavePopBackStack = {})
    }
}
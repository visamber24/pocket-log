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
import com.lazysloth.pocketlog.ui.screen.other.AddTransactionScreenImpl
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
        vm::onAccountSelected,
        vm::onAmountChange,
        vm::onExpandedChange,
        vm::onOptionSelected,
        vm::onTransactionTypeSelected,
        vm::onNoteValueChange,
        vm::onDescriptionChange,
        vm::onClickDate,
        vm::onDateChange,
        onSave = {
            vm.saveTransaction()
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